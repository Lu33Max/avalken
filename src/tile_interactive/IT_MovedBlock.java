package tile_interactive;

import main.GamePanel;

public class IT_MovedBlock extends InteractiveTile {

    GamePanel gp;

    public IT_MovedBlock(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "Moved Block";

        down1 = setup("/interactiveTiles/MovableBlock", 1, 1);
        destructible = true;
    }
}
