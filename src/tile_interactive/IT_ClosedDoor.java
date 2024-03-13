package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_ClosedDoor extends InteractiveTile {

    GamePanel gp;

    public IT_ClosedDoor(GamePanel gp, int col, int row, String dir) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "ClosedDoor";

        switch (dir) {
            case "up" -> down1 = setup("/interactiveTiles/ClosedDoorUp", 2, 2);
            case "down" -> down1 = setup("/interactiveTiles/ClosedDoorDown", 2, 2);
            case "left" -> down1 = setup("/interactiveTiles/ClosedDoorLeft", 2, 2);
            case "right" -> down1 = setup("/interactiveTiles/ClosedDoorRight", 2, 2);
        }

        solidArea = new Rectangle(worldX, worldY, gp.getTILE_SIZE()*2, gp.getTILE_SIZE()*2);
    }
}
