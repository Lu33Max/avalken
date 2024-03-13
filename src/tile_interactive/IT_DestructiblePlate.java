package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_DestructiblePlate extends InteractiveTile {

    GamePanel gp;
    int lockCounter = 0;

    public IT_DestructiblePlate(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        name = "DestructiblePlate";

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;

        down1 = setup("/interactiveTiles/breakable_plate", 1, 1);
        down2 = setup("/interactiveTiles/empty", 1, 1);

        interactionRect = new Rectangle(worldX -1 + gp.getTILE_SIZE() /4, worldY -1 + gp.getTILE_SIZE() /4, gp.getTILE_SIZE() /2 +2, gp.getTILE_SIZE() /2 +2);
        solidArea = new Rectangle(0,0,0,0);
    }

    public void update() {
        if(lockCounter == 5) {
            spriteNum = 1;
            gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
            gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

            if(interactionRect.intersects(gp.getPlayer1().getSolidArea())) {

                if ((gp.getPlayer1().getCurrentItem1() == null || !gp.getPlayer1().getCurrentItem1().getName().equals("Boots")) && (gp.getPlayer1().getCurrentItem2() == null || !gp.getPlayer1().getCurrentItem2().getName().equals("Boots"))) {
                    spriteNum = 2;
                    switch (gp.getPlayer1().getDirection()) {
                        case "up" -> gp.getPlayer1().setWorldY(gp.getPlayer1().getWorldY() + gp.getTILE_SIZE() /2);
                        case "down" -> gp.getPlayer1().setWorldY(gp.getPlayer1().getWorldY() - gp.getTILE_SIZE() /2);
                        case "left" -> gp.getPlayer1().setWorldX(gp.getPlayer1().getWorldX() + gp.getTILE_SIZE() /2);
                        case "right" -> gp.getPlayer1().setWorldX(gp.getPlayer1().getWorldX() - gp.getTILE_SIZE() /2);
                    }
                    gp.getPlayer1().setCurrentLife(gp.getPlayer1().getCurrentLife() -1);
                    lockCounter = 0;
                }
            }

            gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
            gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();

            if(gp.getPlayerCount() == 2) {
                gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getWorldX() + gp.getPlayer2().getSolidArea().x;
                gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getWorldY() + gp.getPlayer2().getSolidArea().y;

                if(interactionRect.intersects(gp.getPlayer2().getSolidArea())) {

                    if ((gp.getPlayer2().getCurrentItem1() == null || !gp.getPlayer2().getCurrentItem1().getName().equals("Boots")) && (gp.getPlayer2().getCurrentItem2() == null || !gp.getPlayer2().getCurrentItem2().getName().equals("Boots"))) {
                        spriteNum = 2;
                        switch (gp.getPlayer2().getDirection()) {
                            case "up" -> gp.getPlayer2().setWorldY(gp.getPlayer2().getWorldY() + gp.getTILE_SIZE() /2);
                            case "down" -> gp.getPlayer2().setWorldY(gp.getPlayer2().getWorldY() - gp.getTILE_SIZE() /2);
                            case "left" -> gp.getPlayer2().setWorldX(gp.getPlayer2().getWorldX() + gp.getTILE_SIZE() /2);
                            case "right" -> gp.getPlayer2().setWorldX(gp.getPlayer2().getWorldX() - gp.getTILE_SIZE() /2);
                        }
                        gp.getPlayer2().setCurrentLife(gp.getPlayer2().getCurrentLife() -1);
                        lockCounter = 0;
                    }
                }

                gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getSolidAreaDefX();
                gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getSolidAreaDefY();
            }

        } else {
            lockCounter++;
        }
    }
}
