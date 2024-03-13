package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_MovableBlock extends InteractiveTile {

    GamePanel gp;
    String dir;

    public IT_MovableBlock(GamePanel gp, int col, int row, String dir) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        this.dir = dir;
        name = "Movable Block";

        down1 = setup("/interactiveTiles/MovableBlock", 1, 1);

        switch (dir) {
            case "up" -> interactionRect = new Rectangle(worldX, worldY, gp.getTILE_SIZE(), gp.getTILE_SIZE() +1);
            case "down" -> interactionRect = new Rectangle(worldX, worldY -1, gp.getTILE_SIZE(), gp.getTILE_SIZE() +1);
            case "left" -> interactionRect = new Rectangle(worldX, worldY, gp.getTILE_SIZE() +1, gp.getTILE_SIZE());
            case "right" -> interactionRect = new Rectangle(worldX -1, worldY, gp.getTILE_SIZE() +1, gp.getTILE_SIZE());
        }

        destructible = true;
    }

    public void update(){
        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

        if (interactionRect.intersects(gp.getPlayer1().getSolidArea()) && gp.getPlayer1().getDirection().equals(dir)) {
            if (gp.getPlayer1().getCurrentItem1() != null && gp.getPlayer1().getCurrentItem1().getName().equals("Power Gloves") || gp.getPlayer1().getCurrentItem2() != null && gp.getPlayer1().getCurrentItem2().getName().equals("Power Gloves")) {
                switch (dir) {
                    case "up" -> worldY -= gp.getTILE_SIZE();
                    case "down" -> worldY += gp.getTILE_SIZE();
                    case "left" -> worldX -= gp.getTILE_SIZE();
                    case "right" -> worldX += gp.getTILE_SIZE();
                }
                gp.getiTile()[gp.getCurrentMap()][index] = getDestroyedForm();
            }
        }

        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();

        if(gp.getPlayerCount() == 2) {
            gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getWorldX() + gp.getPlayer2().getSolidArea().x;
            gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getWorldY() + gp.getPlayer2().getSolidArea().y;

            if (interactionRect.intersects(gp.getPlayer2().getSolidArea()) && gp.getPlayer2().getDirection().equals(dir)) {
                if (gp.getPlayer1().getCurrentItem2() != null && gp.getPlayer2().getCurrentItem1().getName().equals("Power Gloves") || gp.getPlayer2().getCurrentItem2() != null && gp.getPlayer2().getCurrentItem2().getName().equals("Power Gloves")) {
                    switch (dir) {
                        case "up" -> worldY -= gp.getTILE_SIZE();
                        case "down" -> worldY += gp.getTILE_SIZE();
                        case "left" -> worldX -= gp.getTILE_SIZE();
                        case "right" -> worldX += gp.getTILE_SIZE();
                    }
                    gp.getiTile()[gp.getCurrentMap()][index] = getDestroyedForm();
                }
            }

            gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getSolidAreaDefX();
            gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getSolidAreaDefY();
        }
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_MovedBlock(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }
}
