package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_Stairs extends InteractiveTile{

    GamePanel gp;
    boolean up;
    int map, tCol, tRow;

    public IT_Stairs(GamePanel gp, int col, int row, boolean up, int map, int tCol, int tRow) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.getTILE_SIZE();
        this.worldY = row * gp.getTILE_SIZE();
        name = "Stairs";

        this.up = up;
        this.map = map;
        this.tCol = tCol;
        this.tRow = tRow;

        if(up) down1 = setup("/interactiveTiles/StairsUp", 1, 1);
        else down1 = setup("/interactiveTiles/StairsDown", 1, 1);

        solidArea = new Rectangle(0,0,0,0);
        interactionRect = new Rectangle(worldX + gp.getTILE_SIZE() /4, worldY + gp.getTILE_SIZE() / 4, gp.getTILE_SIZE() / 2, gp.getTILE_SIZE() /2);
    }

    public void update() {
        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

        if(interactionRect.intersects(gp.getPlayer1().getSolidArea())) {
            gp.getEventHandler().teleport(map, tCol, tRow);
        }

        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();

        if(gp.getPlayerCount() == 2) {
            gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getWorldX() + gp.getPlayer2().getSolidArea().x;
            gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getWorldY() + gp.getPlayer2().getSolidArea().y;

            if(interactionRect.intersects(gp.getPlayer2().getSolidArea())) {
                gp.getEventHandler().teleport(map, tCol, tRow);
            }

            gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getSolidAreaDefX();
            gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getSolidAreaDefY();
        }
    }
}
