package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    //// KONSTRUKTOR ////
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[200];
        getTileImage();
    }

    //// GRAFISCHES ////
    public void getTileImage() {
        //// Laden der Tile-Bilder ////

        // Platzhalter
        setup(0, "10", false);
        setup(1, "10", true);
        setup(2, "10", true);
        setup(3, "10", false);
        setup(4, "10", true);
        setup(5, "10", false);
        setup(6, "10", false);
        setup(7, "10", false);
        setup(8, "10", false);
        setup(9, "10", false);

        // Oberwelt
        for(int i = 10; i < 28; i++) {
            setup(i, String.valueOf(i), false);
        }
        for(int i = 28; i < 65; i++) {
            setup(i, String.valueOf(i), true);
        }
        setup(66, "hut", false);
        setup(67, "floor01", false);
        setup(68, "wall", true);
        setup(69, "table01", true);
        setup(70, "31", false);
        setup(71, "54", false);
        setup(72, "61", false);
        setup(73, "62", false);
        setup(74, "63", false);
        setup(75, "56", false);
        setup(76, "33", false);

        // Tempel
        setup(99, "99", false);
        setup(100, "100", true);
        for(int i = 101; i < 105; i++) {
            setup(i, String.valueOf(i), false);
        }
        for(int i = 105; i < 131; i++) {
            setup(i, String.valueOf(i), true);
        }
        for(int i = 131; i < 150; i++) {
            setup(i, String.valueOf(i), false);
        }
    }
    public void setup(int index, String imageName, boolean collision) {
        // Die 16x16 Pixel großen Ursprungsbilder werden auf die Tilegröße skaliert

        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.getTILE_SIZE(), gp.getTILE_SIZE());
            tile[index].name = imageName;
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        // Solange col und row im Bereich der Größe der aktuellen Karte sind
        while(col < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxCol() && row < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxRow()) {
            // Die tileNum des aktuellen Tiles wird bestimmt
            int tileNum = gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMapTileNum()[col][row];

            // Die Weltkoordinaten des Tiles werden bestimmt
            int worldX = col * gp.getTILE_SIZE();
            int worldY = row * gp.getTILE_SIZE();
            int screenX, screenY;

            // Im Einzelspieler-Modus
            if(gp.getPlayerCount() == 1) {
                // Die Bildschirmkoordinaten des Tiles werden bestimmt
                screenX = worldX - gp.getPlayer1().getWorldX() + gp.getPlayer1().getScreenX();
                screenY = worldY - gp.getPlayer1().getWorldY() + gp.getPlayer1().getScreenY();

                // Falls das Tile aktuell auf dem Bildschirm sichtbar ist, wird es gezeichnet (Dient zur starken Ressourceneinsparung)
                if (worldX + gp.getTILE_SIZE() > gp.getPlayer1().getWorldX() - gp.getPlayer1().getScreenX() &&
                        worldX - gp.getTILE_SIZE() < gp.getPlayer1().getWorldX() + gp.getPlayer1().getScreenX() &&
                        worldY + gp.getTILE_SIZE() > gp.getPlayer1().getWorldY() - gp.getPlayer1().getScreenY() &&
                        worldY - gp.getTILE_SIZE() < gp.getPlayer1().getWorldY() + gp.getPlayer1().getScreenY()) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }

            // Im Mehrspieler-Modus
            } else {
                // Analog unter Verwendung des Bildschirmmittelpunkts anstelle von Spieler 1
                screenX = worldX - gp.getScreenCenterX() + gp.getSCREEN_WIDTH() /2 - gp.getTILE_SIZE() /2;
                screenY = worldY - gp.getScreenCenterY() + gp.getSCREEN_HEIGHT() /2 - gp.getTILE_SIZE() /2;

                if (worldX + gp.getTILE_SIZE() > gp.getScreenCenterX() - gp.getSCREEN_WIDTH() /2 &&
                        worldX - gp.getTILE_SIZE() < gp.getScreenCenterX() + gp.getSCREEN_WIDTH() /2 &&
                        worldY + gp.getTILE_SIZE() > gp.getScreenCenterY() - gp.getSCREEN_HEIGHT() /2 &&
                        worldY - gp.getTILE_SIZE() < gp.getScreenCenterY() + gp.getSCREEN_HEIGHT() /2) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }

            }
            col++;
            if (col == gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxCol()) {
                col = 0;
                row++;
            }
        }
    }

    //// GETTER ////
    public Tile[] getTile() { return tile; }
}
