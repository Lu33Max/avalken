package tile_interactive;

import main.GamePanel;

public class IT_BlockadeUp extends InteractiveTile {

    GamePanel gp;

    public IT_BlockadeUp(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "BlockadeUp";

        down1 = setup("/interactiveTiles/BlockadeUp", 1, 1);
        destructible = true;
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_BlockadeDown(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }
}
