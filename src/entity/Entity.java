package entity;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_BombExploding;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {

    // CHARAKTER
    protected int worldX, worldY;
    protected int speed;
    protected int currentLife, maxLife;
    int currentArrow, currentBomb;
    boolean bombObtained, bowObtained;
    int level, strength, dexterity, nextLevelExp, money;
    protected int attack, defense, exp;
    protected int shotLockCounter, bombCounter;
    protected boolean bombActive;
    Entity currentWeapon, currentShield, currentItem1, currentItem2;
    protected Projectile projectile;
    protected OBJ_BombExploding explodingBomb;

    // ITEM ATTRIBUTE
    protected int value;
    protected int attackValue, defenseValue;
    protected String description = "";
    protected int useCost;
    protected int price;
    protected boolean stackable = false;
    protected int amount = 1;
    protected int knockBackPower;
    ArrayList<Entity> inventory = new ArrayList<>();
    final int INVENTORY_SLOTS = 28;

    // OBJEKTE UND ENTITIES
    protected BufferedImage image, image2;
    protected String name;
    protected boolean collision = false;
    protected int type; // 0 = player, 1 = npc, 2 = enemy, 3 = sword, 4 = deleted, 5 = shield, 6 = consumable, 7 = coin, 8 = obstacle, 9 = equippable, 10 = Non-interactive Object
    protected boolean alive = true;
    boolean dying = false;
    boolean knockBack = false;
    Entity knockBackUser;
    protected int defaultSpeed;
    int knockBackCounter;
    int dyingCounter;
    protected boolean onPath = false, onPath1 = false, onPath2 = false;

    // GRAFISCHES
    GamePanel gp;
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    BufferedImage atkUp1, atkUp2, atkUp3, atkDown1, atkDown2, atkDown3, atkLeft1, atkLeft2, atkLeft3, atkRight1, atkRight2, atkRight3;
    protected String direction = "down";
    protected int actionLock;

    // KAMPF
    boolean attacking = false;
    boolean invincible = false;
    int invincibleCounter;
    protected Rectangle attackArea = new Rectangle(0,0,0,0);
    boolean hpBarOn = false;
    int hpBarCounter;

    // BEWEGUNG
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    int idleCounter = 0;

    // KOLLISION
    protected Rectangle solidArea;
    protected int solidAreaDefX, solidAreaDefY;
    boolean collisionOn = false;

    // DIALOG
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    //// KONSTRUKTOR ////
    public Entity(GamePanel gp) {
        this.gp = gp;

        solidArea = new Rectangle(0, 0, gp.getTILE_SIZE(), gp.getTILE_SIZE());
    }

    //// REAKTIONEN ////
    public void setAction() { }
    public void damageReaction(int playNum) { }
    public void interact() {}
    public void speak(Entity player) {
        if(dialogues[dialogueIndex] == null) dialogueIndex = 0;

        // Setzt den aktuellen Dialog auf den Dialog des NPCs
        gp.getUi().currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // Dreht NPC zum Spieler hin
        switch (player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    public boolean consume(Entity entity) { return false;}
    public void use(Entity user) {}

    //// ITEM DROPS ////
    public void checkDrop() { }
    public void dropItem(Entity drop) {
        // Sucht nach dem ersten freien Feld im Object Array und setzt es auf den Drop des Gegners
        for(int i = 0; i < gp.getObj()[gp.getCurrentMap()].length; i++) {
            if(gp.getObj()[gp.getCurrentMap()][i] == null) {
                gp.setObj(gp.getCurrentMap(), i, drop);
                gp.getObj()[gp.getCurrentMap()][i].worldX = worldX;
                gp.getObj()[gp.getCurrentMap()][i].worldY = worldY;
                break;
            }
        }
    }

    //// PARTIKEL ////
    public Color getParticleColor() {
        return null;
    }
    public int getParticleSpeed() {
        return 0;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = gp.getTILE_SIZE() / 8;
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getMaxLife();

        // Erzeugt vier neue Partikel mit den Werten des Auslösers
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

        // Hinzufügen dieser zur particleList
        gp.getParticleList().add(p1);
        gp.getParticleList().add(p2);
        gp.getParticleList().add(p3);
        gp.getParticleList().add(p4);
    }

    //// KOLLISION ////
    public void checkCollision() {
        collisionOn = false;
        // Überprüft, ob diese Entity mit einem soliden Tile, einem Objekt, einem NPC, einem Gegner, einem interaktiven Tile oder einem Spieler zusammenstößt
        gp.getCollisionCheck().checkTile(this);
        gp.getCollisionCheck().checkObject(this, false);
        gp.getCollisionCheck().checkEntity(this, gp.getNpc());
        gp.getCollisionCheck().checkEntity(this, gp.getEnm());
        gp.getCollisionCheck().checkEntity(this, gp.getiTile());
        boolean hitPlayer1 = gp.getCollisionCheck().checkPlayer(this, gp.getPlayer1());
        boolean hitPlayer2 = gp.getCollisionCheck().checkPlayer(this, gp.getPlayer2());

        // Falls diese Entity ein Gegner ist, einen Spieler berührt und dieser nicht unverwundbar ist, fügt der Gegner diesem Spieler Schaden zu
        if(this.type == 2 && hitPlayer1 && !gp.getPlayer1().invincible) {
            damagePlayer(attack, gp.getPlayer1());
        }
        if(this.type == 2 && hitPlayer2 && !gp.getPlayer2().invincible) {
            damagePlayer(attack, gp.getPlayer2());
        }
    }
    public void damagePlayer(int attack, Player player) {
        // Spielt den Schaden-Soundeffekt
        gp.playSoundEffect(6);

        // Der Schaden wird als Differenz aus gegnerischem Angriff und eigener Verteidigung berechnet. Ist diese kleiner 0, wird sie auf 0 gesetzt, damit kein Leben regenriert wird
        int damage = attack - player.getDefense();
        if(damage < 0) damage = 0;

        // Dem Spieler werden die Leben abgezogen und er wird unverwundbar
        player.currentLife -= damage;
        player.invincible = true;
    }

    //// UPDATE ////
    public void update() {

        // Test, ob die Entity aktuell Rückstoß erhalten hat
        if(knockBack) {

            // Test auf Kollision
            checkCollision();

            // Falls eine Kollision passiert, wird der Rückstoß unterbrochen
            if(collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;

            // Ansonsten wird die Entity in die entgegengesetzte Richtung des Verursachers bewegt
            } else {
                switch(knockBackUser.direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Der knockBackCounter wird erhöht und falls dieser 10 erreicht, wird der Rückstoß abgebrochen
            knockBackCounter++;
            if(knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                knockBackUser = null;
                speed = defaultSpeed;
            }

        // Falls kein Rückstoß ist
        } else {

            // Entity führt ihr setAction aus
            setAction();
            // Teste auf Kollision
            checkCollision();

            // Falls keine Kollision ist bewegt sich die Entity mit ihrer Geschwindigkeit
            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
        }

        // Der aktuelle Sprite wird alle 12 Frames gewechselt
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) { spriteNum = 2; }
            else if (spriteNum == 2) { spriteNum = 1; }
            spriteCounter = 0;
        }

        // Ist die Entity unverwundbar wird der Zähler erhöht
        if(invincible) {
            invincibleCounter++;
            // Nach 30 Frames ist die unverwundbarkeit zu Ende und der Zähler wird wieder auf 0 gesetzt
            if(invincibleCounter == gp.getFPS() / 2) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Der shotLockCounter wird jeden Frame erhöht
        if(shotLockCounter < 90) {
            shotLockCounter++;
        }
    }

    //// GRAFISCHES ////
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX, screenY;

        //// Berechnung der Anzeigekoordinaten auf dem Bildschirm ////
        // Im Einzelspieler wird die Position auf dem Bildschirm relativ zur Bildschirmposition des Spielers berechnet
        if(gp.getPlayerCount() == 1) {
            // Ein halbes Tile wird abgezogen, da die Entities von der oberen linken Ecke aus gezeichnet werden
            screenX = worldX - gp.getPlayer1().worldX + gp.getSCREEN_WIDTH() / 2 - gp.getTILE_SIZE() / 2;
            screenY = worldY - gp.getPlayer1().worldY + gp.getSCREEN_HEIGHT() / 2 - gp.getTILE_SIZE() / 2;
        // Im Mehrspieler wird zur Berechnung die Weltkoordinaten des Bildschirmmittelpunkts genutzt
        } else {
            // Ein halbes Tile wird abgezogen, da die Entities von der oberen linken Ecke aus gezeichnet werden
            screenX = worldX - gp.getScreenCenterX() + gp.getSCREEN_WIDTH() / 2 - gp.getTILE_SIZE() / 2;
            screenY = worldY - gp.getScreenCenterY() + gp.getSCREEN_HEIGHT() / 2 - gp.getTILE_SIZE() / 2;
        }

        // Es wird geprüft, ob die Entity für den/die Spieler aktuell auf dem Bildschirm sichtbar ist
        if(gp.getPlayerCount() == 1 && worldX + gp.getTILE_SIZE() > gp.getPlayer1().worldX - gp.getPlayer1().screenX &&
           worldX - gp.getTILE_SIZE() < gp.getPlayer1().worldX + gp.getPlayer1().screenX &&
           worldY + gp.getTILE_SIZE() > gp.getPlayer1().worldY - gp.getPlayer1().screenY &&
           worldY - gp.getTILE_SIZE() < gp.getPlayer1().worldY + gp.getPlayer1().screenY ||
           gp.getPlayerCount() == 2 && worldX + gp.getTILE_SIZE() > gp.getScreenCenterX() - gp.getSCREEN_WIDTH() /2 &&
           worldX - gp.getTILE_SIZE() < gp.getScreenCenterX() + gp.getSCREEN_WIDTH() /2 &&
           worldY + gp.getTILE_SIZE() > gp.getScreenCenterY() - gp.getSCREEN_HEIGHT() /2 &&
           worldY - gp.getTILE_SIZE() < gp.getScreenCenterY() + gp.getSCREEN_HEIGHT() /2) {

            // Je nach aktueller Bewegungsrichtung und aktuellem Sprite wird das zu zeichnende Bild geändert
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                }
                case "down" -> {
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                }
                case "left" -> {
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                }
                case "right" -> {
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                }
            }

            //// Gegnerischer Lebensbalken ////
            // Test, ob die Entity vom Typ Gegner ist und der Lebensbalken aktuell gezeigt werden soll
            if(type == 2 && hpBarOn) {
                // Die Länge eines Lebenspunkts wird berechnet
                double oneScale = (float) gp.getTILE_SIZE() / maxLife;
                // Die Länge des aktuellen Lebens wird berechnet
                double hpBarValue = oneScale * currentLife;

                // Zeichnet ein weißes Rechteck als Hintergrund
                g2.setColor(Color.white);
                g2.fillRect(screenX - (gp.getTILE_SIZE() / 20), screenY - (gp.getTILE_SIZE() / 3) - (gp.getTILE_SIZE() / 20), gp.getTILE_SIZE() + (gp.getTILE_SIZE() / 10), (gp.getTILE_SIZE() / 5) + (gp.getTILE_SIZE() / 10));
                // Zeichnet ein graues Rechteck als leeren Lebensbalken
                g2.setColor(Color.darkGray);
                g2.fillRect(screenX, screenY - (gp.getTILE_SIZE() / 3), gp.getTILE_SIZE(), gp.getTILE_SIZE() / 5);
                // Zeichnet den aktuellen Lebensbalken in Rot
                g2.setColor(Color.red);
                g2.fillRect(screenX, screenY - (gp.getTILE_SIZE() / 3), (int) hpBarValue, gp.getTILE_SIZE() / 5);

                // Erhöht den hpBarCounter. Nach 10 Sekunden wird der Lebensbaken wieder ausgeblendet
                hpBarCounter++;
                if(hpBarCounter > gp.getFPS() * 10) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            // Ist die Entity aktuell unverwundbar, wird sie mit halber Deckkraft gezeichnet
            if(invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
            }

            // Stirbt die Entity, wird die Todesanimation gespielt
            if(dying) {
                dyingAnimation(g2);
            }

            // Das Bild der Entity wird gezeichnet
            g2.drawImage(image, screenX, screenY, null);
            // Die Deckkraft wird zurückgesetzt
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
    }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        // Nach 5/15/25 Frames wird die Entity unsichtbar
        if(dyingCounter % 5 == 0 && dyingCounter % 2 != 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        }
        // Nach 10/20/30 Frames wird die Entity wieder sichtbar
        if(dyingCounter % 5 == 0 && dyingCounter % 2 == 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
        // Nach einer halben Sekunde endet die Animation und die Entity gilt als besiegt
        if(dyingCounter > gp.getFPS() / 2) {
            alive = false;
            dyingCounter = 0;
        }
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage scaledImage = null;

        // Die Sprites der Entity, die als 16x16 Pixel geladen werden, werden auf die Tilegröße skaliert
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            scaledImage = utilityTool.scaleImage(scaledImage, gp.getTILE_SIZE() * width, gp.getTILE_SIZE() * height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    //// PFADSUCHE ////
    public void searchPath(int endCol, int endRow) {
        // Die Position der Entity wird als StartNode und die übergebenen Werte als EndNode an den PathFinder übergeben
        int startCol = (worldX + solidArea.x) / gp.getTILE_SIZE(), startRow = (worldY + solidArea.y) / gp.getTILE_SIZE();
        gp.getPathFinder().setNode(startCol, startRow, endCol, endRow);

        // Falls der PathFinder den Weg sucht (bzw. die Entity das Ziel noch nicht erreicht hat)
        if(gp.getPathFinder().search()) {
            // Bestimmt die nächsten Weltkoordinaten der Entity
            int nextX = gp.getPathFinder().getPathList().get(0).getCol() * gp.getTILE_SIZE();
            int nextY = gp.getPathFinder().getPathList().get(0).getRow() * gp.getTILE_SIZE();

            // Bestimmt die aktuelle Position der solidArea
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // Liegt das Ziel genau über der Entity, ändert sie ihre Richtung nach oben
            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.getTILE_SIZE()) {
                direction = "up";

            // Liegt das Ziel genau unter der Entity, ändert sie ihre Richtung nach unter
            } else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.getTILE_SIZE()) {
                direction = "down";

            // Liegt das Ziel genau neben der Entity
            } else if(enTopY >= nextY && enBottomY < nextY + gp.getTILE_SIZE()) {
                // Test, ob links oder rechts und entsprechende Richtungsänderung
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }

            // Liegt das Ziel oberhalb und links, geht sie zunächst nach oben und bei einer Kollision nach links
            } else if(enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }

            // Analog
            } else if(enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }

            // Analog
            } else if(enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }

            // Analog
            } else if(enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }

            // test, ob die nächste Position der EndNode entspricht. Tut es diese, wird die Pfadsuche beendet
            int nextCol = gp.getPathFinder().getPathList().get(0).getCol();
            int nextRow = gp.getPathFinder().getPathList().get(0).getRow();
            if(nextCol == endCol && nextRow == endRow) {
                onPath = false;
            }
        }
    }

    //// GETTER ////
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public int getSpeed() { return speed; }
    public int getCurrentLife() {return currentLife; }
    public int getMaxLife() { return maxLife; }
    public int getCurrentArrow() { return currentArrow; }
    public int getCurrentBomb() {return currentBomb; }
    public int getLevel() { return level; }
    public int getStrength() { return strength; }
    public int getAttack() { return attack; }
    public int getDexterity() { return dexterity; }
    public int getDefense() { return defense; }
    public int getExp() { return exp; }
    public int getNextLevelExp() { return nextLevelExp; }
    public int getMoney() { return money; }

    public int getShotLockCounter() { return shotLockCounter; }
    public int getBombCounter() { return bombCounter; }

    public Entity getCurrentWeapon() { return currentWeapon; }
    public Entity getCurrentShield() { return currentShield; }
    public Entity getCurrentItem1() { return currentItem1; }
    public Entity getCurrentItem2() { return currentItem2; }

    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public int getAmount() { return amount; }

    public ArrayList<Entity> getInventory() { return inventory; }

    public String getName() { return name; }
    public boolean isCollision() { return collision; }
    public int getType() { return type; }
    public boolean isAlive() { return alive; }
    public boolean isDying() { return dying; }

    public BufferedImage getDown1() { return down1; }
    public BufferedImage getDown2() { return down2; }

    public String getDirection() { return direction; }
    public boolean isInvincible() { return invincible; }
    public Rectangle getSolidArea() { return solidArea; }
    public int getSolidAreaDefX() { return solidAreaDefX; }
    public int getSolidAreaDefY() { return solidAreaDefY; }

    public boolean isBombObtained() { return bombObtained; }
    public boolean isBowObtained() { return  bowObtained; }

    //// SETTER ////
    public void setWorldX(int x) { worldX = x; }
    public void setWorldY(int y) { worldY = y; }
    public void setCurrentLife(int currentLife) { this.currentLife = currentLife; }
    public void setCurrentArrow(int currentArrow) { this.currentArrow = currentArrow; }
    public void setCurrentBomb(int currentBomb) { this.currentBomb = currentBomb; }
    public void setMoney(int money) { this.money = money; }

    public void setBombActive(boolean bombActive) { this.bombActive = bombActive; }

    public void setAmount(int amount) { this.amount = amount; }

    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }
    public void setShotLockCounter(int shotLockCounter) { this.shotLockCounter = shotLockCounter; }

    public void setBombObtained(boolean bombObtained) { this.bombObtained = bombObtained; }
    public void setBowObtained(boolean bowObtained) { this.bowObtained = bowObtained; }
}
