package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_ButtonOn extends InteractiveTile {

    GamePanel gp;

    public IT_ButtonOn (GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "ButtonOn";

        solidArea = new Rectangle(0,0,0,0);
        down1 = setup("/interactiveTiles/ButtonOn", 1, 1);
    }
}
