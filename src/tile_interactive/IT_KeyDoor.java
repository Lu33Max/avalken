package tile_interactive;

import main.GamePanel;

import java.awt.Rectangle;

public class IT_KeyDoor extends InteractiveTile {

    GamePanel gp;

    public IT_KeyDoor(GamePanel gp, int col, int row, String dir) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "KeyDoor";

        switch (dir) {
            case "up" -> down1 = setup("/interactiveTiles/KeyDoorUp", 2, 2);
            case "down" -> down1 = setup("/interactiveTiles/KeyDoorDown", 2, 2);
            case "left" -> down1 = setup("/interactiveTiles/KeyDoorLeft", 2, 2);
            case "right" -> down1 = setup("/interactiveTiles/KeyDoorRight", 2, 2);
        }

        destructible = true;
        solidArea = new Rectangle(worldX, worldY, gp.getTILE_SIZE()*2, gp.getTILE_SIZE()*2);
        interactionRect = new Rectangle(worldX-1, worldY-1, gp.getTILE_SIZE()*2 +2, gp.getTILE_SIZE()*2 +2);
    }

    public void update() {
        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

        if(gp.getPlayer1().getSolidArea().intersects(interactionRect)) {
            int i = gp.getPlayer1().findItemInInventory("Key");
            if (i != 999) {
                if (gp.getPlayer1().getInventory().get(i).getAmount() > 0) {
                    if (gp.getPlayer1().getInventory().get(i).getAmount() > 1) {
                        gp.getPlayer1().getInventory().get(i).setAmount(gp.getPlayer1().getInventory().get(i).getAmount() -1);
                    } else {
                        gp.getPlayer1().getInventory().remove(i);
                    }
                    gp.getiTile()[gp.getCurrentMap()][this.index] = null;
                }
            }
        }

        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();
    }
}
