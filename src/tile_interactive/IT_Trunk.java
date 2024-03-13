package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "Trunk";

        down1 = setup("/interactiveTiles/trunk", 1, 1);
        solidArea.x = 0; solidArea.y = 0; solidArea.height = 0; solidArea.width = 0; solidAreaDefX = 0; solidAreaDefY = 0;
    }
}
