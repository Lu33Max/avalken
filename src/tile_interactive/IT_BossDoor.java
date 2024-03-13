package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_BossDoor extends InteractiveTile {

    GamePanel gp;

    public IT_BossDoor(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "BossDoor";

        down1 = setup("/interactiveTiles/BossDoor", 2, 2);

        destructible = true;
        solidArea = new Rectangle(worldX, worldY, gp.getTILE_SIZE()*2, gp.getTILE_SIZE()*2);
        interactionRect = new Rectangle(worldX-1, worldY-1, gp.getTILE_SIZE()*2 +2, gp.getTILE_SIZE()*2 +2);
    }

    public void update() {
        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

        if(gp.getPlayer1().getSolidArea().intersects(interactionRect)) {
            int i = gp.getPlayer1().findItemInInventory("BossKey");
            if (i != 999) {
                if (gp.getPlayer1().getInventory().get(i).getAmount() > 0) {
                    gp.getPlayer1().getInventory().remove(i);
                    gp.getiTile()[gp.getCurrentMap()][this.index] = null;
                }
            }
        }

        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();
    }
}
