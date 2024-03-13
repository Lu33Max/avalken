package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BasicSword extends Entity {

    public OBJ_BasicSword (GamePanel gp){
        super(gp);

        type = 3;
        name = "Basic Sword";
        down1 = setup("/objects/sword_normal", 1, 1);
        down2 = setup("/objects/sword_normal", 2, 2);
        attackValue = 1;
        attackArea.width = (int) (gp.getTILE_SIZE() * (2F/3F));
        attackArea.height = (int) (gp.getTILE_SIZE() * (2F/3F));
        description = "[ Einfaches Schwert ]\nEin altes Schwert. Nichts\nbesonderes";
        price = 40;
        knockBackPower = 5;
    }
}
