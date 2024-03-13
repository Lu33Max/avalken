package tile_interactive;

import main.GamePanel;
import object.OBJ_Chest;
import object.OBJ_Key;

import java.awt.*;

public class IT_ButtonOff extends InteractiveTile {

    GamePanel gp;

    public IT_ButtonOff(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "ButtonOff";

        down1 = setup("/interactiveTiles/ButtonOff", 1, 1);
        solidArea = new Rectangle(0,0,0,0);
        interactionRect = new Rectangle(worldX + gp.getTILE_SIZE() /4, worldY + gp.getTILE_SIZE() /4, gp.getTILE_SIZE() /2, gp.getTILE_SIZE() /2);
    }

    public void update() {
        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

        if(interactionRect.intersects(gp.getPlayer1().getSolidArea())) {
            gp.getiTile()[gp.getCurrentMap()][this.index] = getDestroyedForm();

            if(gp.getCurrentMap() == gp.getDungeon1Maps() +8) {
                for(int i = 0; i < gp.getObj()[0].length; i++) {
                    if(gp.getObj()[gp.getCurrentMap()][i] == null) {
                        gp.setObj(gp.getCurrentMap(), i, new OBJ_Chest(gp, new OBJ_Key(gp)));
                        gp.getObj()[gp.getCurrentMap()][i].setWorldX(gp.getTILE_SIZE() *43);
                        gp.getObj()[gp.getCurrentMap()][i].setWorldY(gp.getTILE_SIZE() *17);
                    }
                }
            }
        }

        gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
        gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_ButtonOn(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }
}
