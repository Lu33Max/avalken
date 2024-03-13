package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = "Boots";
        down1 = setup("/objects/boots", 1, 1);
        down2 = setup("/objects/boots", 2 ,2);
        type = 9;
        description = "[ Stiefel ]\nSie lassen dich leichter fuehlen.";
    }
}
