package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WoodenShield extends Entity {

    public OBJ_WoodenShield (GamePanel gp){
        super(gp);

        type = 5;
        name = "Wooden Shield";
        down1 = setup("/objects/shield_wood", 1, 1);
        down2 = setup("/objects/shield_wood", 2, 2);
        defenseValue = 1;
        description = "[ Holzschild ]\nEin alter Schild. Nicht der\nBeste, aber besser als nichts";
        price = 30;
    }
}
