package tile_interactive;

import main.GamePanel;

import java.awt.Rectangle;

public class IT_BlockadeDown extends InteractiveTile {

    GamePanel gp;

    public IT_BlockadeDown(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "BlockadeDown";

        down1 = setup("/interactiveTiles/BlockadeDown", 1, 1);

        solidArea = new Rectangle(0,0,0,0);
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_BlockadeUp(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }
}
