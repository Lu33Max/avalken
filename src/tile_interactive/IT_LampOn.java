package tile_interactive;

import main.GamePanel;

public class IT_LampOn extends InteractiveTile {

    GamePanel gp;

    public IT_LampOn(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "LampOn";

        down1 = setup("/interactiveTiles/lamp_on1", 1, 1);
        down2 = setup("/interactiveTiles/lamp_on2", 1, 1);
        destructible = true;
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }
    }
}
