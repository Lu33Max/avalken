package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueShield extends Entity {

    public OBJ_BlueShield (GamePanel gp){
        super(gp);

        type = 5;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", 1, 1);
        down2 = setup("/objects/shield_blue", 2, 2);
        defenseValue = 2;
        description = "[ Blauer Schild ]\nEin glaenzender blauer Schild.";
        price = 100;
    }
}
