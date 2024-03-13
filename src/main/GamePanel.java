package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import map.MapManager;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // TILE GRÖßEN
    final int ORIGINAL_TILE_SIZE = 16;  //16x16 tile
    final int SCALE = 3;
    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //Displayed Tilesize

    // HUD GRÖßEN
    final int ORIGINAL_HUD_SIZE = 16;
    final int HUD_SIZE = ORIGINAL_HUD_SIZE * SCALE;

    // FENSTER ATTRIBUTE
    final int MAX_SCREEN_COL = 32;
    final int MAX_SCREEN_ROW = 18;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    int screenCenterX, screenCenterY;

    // WELT ATTRIBUTE
    int maxWorldCol = 96;
    int maxWorldRow = 72;
    final int maxMap = 100;
    int currentMap = 0;
    int dungeon1Maps, dungeon2Maps;

    // FULLSCREEN
    int FULLSCREEN_WIDTH = SCREEN_WIDTH;
    int FULLSCREEN_HEIGHT = SCREEN_HEIGHT;
    BufferedImage tempScreen;
    Graphics2D g2;
    boolean fullScreenOn = false;

    // FPS
    final int FPS = 60;

    // SYSTEM
    CollisionCheck collisionCheck = new CollisionCheck(this);
    AssetSetter assetSetter = new AssetSetter(this);
    TileManager tileManager = new TileManager(this);
    MapManager mapManager = new MapManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    EventHandler eventHandler = new EventHandler(this);
    PathFinder pathFinder = new PathFinder(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    UI ui = new UI(this);
    Config config = new Config(this);
    EnvironmentManager environmentManager = new EnvironmentManager(this);
    Thread gameThread;

    // ENTITIES UND OBJEKTE
    Player player1 = new Player(this, keyHandler, 1);
    Player player2  = new Player(this, keyHandler, 2);
    int playerCount;
    Entity[][] obj = new Entity[maxMap][50];
    Entity[][] npc = new Entity[maxMap][50];
    Entity[][] enm = new Entity[maxMap][50];
    InteractiveTile[][] iTile = new InteractiveTile[maxMap][80];
    Entity[][] projectileList = new Entity[maxMap][50];
    ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int DIALOGUE_STATE = 3;
    public final int CHARACTER_STATE = 4;
    public final int OPTION_STATE = 5;
    public final int GAME_OVER_STATE = 6;
    public final int TRANSITION_STATE = 7;
    public final int TRADE_STATE = 8;

    //// KONSTRUKTOR ////
    public GamePanel() {
        // Grundsätzliche Einstellungen zum Spielfenster
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    //// SETUP ////
    public void setupGame() {
        // Die verschiedenen Objekte führen ihr jeweiliges SetUp durch
        mapManager.setUp();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setEnemy();
        assetSetter.setInteractiveTile();
        setMaxWorld(mapManager.getMapList().get(currentMap).getMaxCol(), mapManager.getMapList().get(currentMap).getMaxCol());
        environmentManager.setup();

        // Das Spiel startet im Titelbildschirm
        gameState = TITLE_STATE;

        // Ein neuer tempScreen wird erzeugt, auf dem vorläufig alle Grafiken gezeichnet werden
        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        // Wurde in den Einstellungen Fullscreen aktiviert, wird das Spiel in den Fullscreen-Modus gesetzt
        if(fullScreenOn) setFullscreen();
    }
    public void setFullscreen() {
        // Erhalt der Bildschirmeigenschaften des Nutzers
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        // Setzt das Spiel in den Fullscreen-Modus
        gd.setFullScreenWindow(Main.window);

        // Speichert Höhe und Weite des Bildschirms
        FULLSCREEN_WIDTH = Main.window.getWidth();
        FULLSCREEN_HEIGHT = Main.window.getHeight();
    }
    public void startGameThread() {
        // Instanziiert einen neuen Thread und startet diesen
        gameThread = new Thread(this);
        gameThread.start();
    }

    //// LAUFZEIT UPDATES UND ZEICHNEN ////
    public void run(){
        double drawInterval = 1000000000F / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Solange der GameThread existiert
        while (gameThread != null) {
            // Die aktuelle Laufzeit wird gespeichert
            currentTime = System.nanoTime();
            // Das Delta entspricht der Zeit, die seit dem letzten Zeichnen vergangen ist im Verhältnis zum Zeichenintervall
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Erreicht Delta, also das Verhältnis zwischen letzter Zeichenzeit und dem gewünschten Zeichenintervall, mindestens 1, wird ein Update durchgeführt, alles neu gezeichnet und das Delta wieder verringert
            if(delta >= 1) {
                update();
                drawToTempScreen(); //draw on the buffered image
                drawToScreen(); //draw the buffered image to the screen
                delta--;
            }
        }
    }
    public void update() {
        // Entities, Spieler etc. werden nur im PlayState geupdated
        if(gameState == PLAY_STATE) {

            //// Spielerupdate ////
            player1.update();
            if(playerCount == 2) {
                player2.update();
            }
            // Position der Bildschirmmitte in Weltkoordinaten wird festgelegt (nur für Mehrspieler von Bedeutung)
            screenCenterX = player1.getWorldX() - ((player1.getWorldX() - player2.getWorldX()) /2);
            screenCenterY = player1.getWorldY() - ((player1.getWorldY() - player2.getWorldY()) /2);

            //// NPC-Update ////
            // NPC-Array wird durchgegangen
            for(int i = 0; i < npc[currentMap].length; i++) {
                // Falls an dieser Stelle ein NPC existiert wird er geupdated
                if(npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            //// Non-Interactive Objekte ////
            // Analog
            for(int i = 0; i < obj[currentMap].length; i++) {
                if(obj[currentMap][i] != null && obj[currentMap][i].getType() == 10) {
                    obj[currentMap][i].update();
                }
            }

            //// Gegner ////
            // Anlog
            for(int i = 0; i < enm[currentMap].length; i++) {
                if(enm[currentMap][i] != null) {
                    // Falls der Gegner am Leben ist, wird er geupdated
                    if(enm[currentMap][i].isAlive() && !enm[currentMap][i].isDying()) {
                        enm[currentMap][i].update();
                    }
                    // Falls der Gegner besiegt wurde, wird geprüft, ob er Items fallen lässt und anschließend wird er aus dem Array entfernt
                    if(!enm[currentMap][i].isAlive()) {
                        enm[currentMap][i].checkDrop();
                        enm[currentMap][i] = null;
                    }
                }
            }

            //// Projektile ////
            // Analog
            for(int i = 0; i < projectileList[currentMap].length; i++) {
                if(projectileList[currentMap][i] != null) {
                    if(projectileList[currentMap][i].isAlive()) projectileList[currentMap][i].update();
                    if(!projectileList[currentMap][i].isAlive()) projectileList[currentMap][i] = null;
                }
            }

            //// Partikel ////
            // Analog
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).isAlive()) particleList.get(i).update();
                    if(!particleList.get(i).isAlive()) particleList.remove(i);
                }
            }

            //// Interaktive Tiles ////
            // Analog
            for(int i =0; i < iTile[0].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            //// Aktuelle Karte ////
            mapManager.getMapList().get(currentMap).update();
        }
    }
    public void drawToTempScreen() {
        // Befindet man sich im Titelbildschirm, wird dieser von der ui gezeichnet
        if(gameState == TITLE_STATE) {
            ui.draw(g2);

        // In allen anderen GameStates
        } else {
            //// ZEICHNEN DER TILES ////
            // Die Tiles werden gezeichnet
            tileManager.draw(g2);

            // Die interaktiven Tiles werden gezeichnet
            for(int i = 0; i < iTile[currentMap].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //// ZEICHNEN DER ENTITIES ////
            // Damit alle Entities in der richtigen Reihenfolge bezüglich ihrer y-Position gezeichnet werden, also das Entities, die sich weiter unten im Bild befinden vor Entities weiter hinten
            // im Bild gezeichnet werden, werden sämtliche Entities an eine Arraylist übergeben, diese dann nach dem worldY-Wert sortiert und in dieser Reihenfolge gezeichnet.

            // Übergibt Spieler an EntityList
            entityList.add(player1);
            if(playerCount == 2) {
                entityList.add(player2);
            }
            // Übergibt NPCs an EntityList
            for(int i = 0; i < npc[currentMap].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            // Übergibt Objekte an EntityList
            for(int i = 0; i < obj[currentMap].length; i++) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            // Übergibt Gegner an EntityList
            for(int i = 0; i < enm[currentMap].length; i++) {
                if(enm[currentMap][i] != null) {
                    entityList.add(enm[currentMap][i]);
                }
            }
            // Übergibt projektile an EntityList
            for(int i = 0; i < projectileList[currentMap].length; i++) {
                if(projectileList[currentMap][i] != null) {
                    entityList.add(projectileList[currentMap][i]);
                }
            }
            // Übergibt Partikel an EntityList
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // Elemente der EntityList werden nach ihrer worldY sortiert
            entityList.sort(Comparator.comparingInt(e -> e.getWorldY()));

            // Alle Entities werden gezeichnet
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

            // Die umgebungseffekte werden gezeichnet
            if(mapManager.getMapList().get(currentMap).getDark()) {
                // Hat der Spieler aktuell eine Laterne ausgerüstet, wird ein großer Lichtkegel gezeichnet
                if(player1.getCurrentItem1() != null && player1.getCurrentItem1().getName().equals("Lantern") || player1.getCurrentItem2() != null && player1.getCurrentItem2().getName().equals("Lantern")) {
                    environmentManager.draw2(g2);

                // Ansonsten wird ein kleinerer Lichtkegel gezeichnet
                } else {
                    environmentManager.draw(g2);
                }
            }

            // Die Benutzeroberfläche wird gezeichnet
            ui.draw(g2);
        }
    }
    public void drawToScreen() {
        // Passt die Größe des TempScreens an die Fullscreen Größe des Nutzers an
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, FULLSCREEN_WIDTH, FULLSCREEN_HEIGHT, null);
        g.dispose();
    }

    //// ZURÜCKSETZEN ////
    public void retry() {
        // Die Positionen der Spieler und die aktuelle Karte werden zurückgesetzt, aber gesammelte Gegenstände bleiben erhalten
        if(playerCount == 1) {
            player1.setDefaultPosition1P();
            player1.restoreLife();
            currentMap = 0;
        }
        if(playerCount == 2) {
            player1.setDefaultValues2P();
            player2.setDefaultValues2P();
            currentMap = dungeon2Maps;
        }
        // Die Größe der aktuellen Karte wird geladen
        setMaxWorld(mapManager.getMapList().get(currentMap).getMaxCol(), mapManager.getMapList().get(currentMap).getMaxCol());

        // NPCs und Gegner der aktuellen Karte werden gesetzt
        assetSetter.setNPC();
        assetSetter.setEnemy();
    }
    public void restart() {
        // Sämtliche Werte der Spieler und Entities werden zurückgesetzt
        player1.setDefaultValues1P();
        player1.setItems1P();

        if(playerCount == 2) {
            player2.setDefaultValues1P();
            player2.setItems1P();
        }

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setEnemy();
        assetSetter.setInteractiveTile();
    }

    //// MUSIK UND SOUNDEFFEKTE ////
    public void playMusic (int i) {
        // Die Musik an der angegebenen Stelle wird wiederholt gespielt
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        // Die angegebene Musik wird unterbrochen
        music.stop();
    }
    public void playSoundEffect(int i) {
        // Der angegebene Soundeffekt wird einmal gespielt
        soundEffect.setFile(i);
        soundEffect.play();
    }

    //// GETTER ////
    public int getSCALE() { return SCALE;}
    public int getTILE_SIZE() { return TILE_SIZE; }
    public int getHUD_SIZE() { return HUD_SIZE; }

    public int getSCREEN_HEIGHT() { return SCREEN_HEIGHT; }
    public int getSCREEN_WIDTH() { return SCREEN_WIDTH; }
    public int getScreenCenterX() {return screenCenterX;}
    public int getScreenCenterY() {return screenCenterY;}
    public int getMaxWorldCol() { return maxWorldCol; }
    public int getMaxWorldRow() { return maxWorldRow; }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getPlayerCount() { return playerCount; }

    public CollisionCheck getCollisionCheck() { return collisionCheck; }

    public int getCurrentMap() { return currentMap; }
    public int getDungeon1Maps() { return dungeon1Maps; }
    public int getDungeon2Maps() { return dungeon2Maps; }

    public int getFPS() { return FPS; }

    public TileManager getTileManager() { return tileManager; }
    public MapManager getMapManager() { return mapManager; }
    public EventHandler getEventHandler() { return eventHandler; }
    public PathFinder getPathFinder() { return pathFinder; }
    public UI getUi() { return ui; }

    public Entity[][] getObj() { return obj; }
    public Entity[][] getNpc() { return npc; }
    public Entity[][] getEnm() { return enm; }
    public InteractiveTile[][] getiTile() { return iTile; }
    public Entity[][] getProjectileList() { return projectileList; }
    public ArrayList<Entity> getParticleList() { return particleList; }
    public int getGameState() { return gameState; }

    //// SETTER ////
    public void setMaxWorld(int col, int row) {
        maxWorldCol = col;
        maxWorldRow = row;
    }
    public void setPlayerCount(int pCount) {
        playerCount = pCount;
    }
    public void setCurrentMap(int currentMap) { this.currentMap = currentMap; }
    public void setDungeon1Maps(int dungeon1Maps) { this.dungeon1Maps = dungeon1Maps; }
    public void setDungeon2Maps(int dungeon2Maps) { this.dungeon2Maps = dungeon2Maps; }
    public void setObj(int i, int j, Entity obj) { this.obj[i][j] = obj; }
    public void setGameState(int gameState) { this.gameState = gameState; }
}
