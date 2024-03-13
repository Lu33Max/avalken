package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PowerGloves extends Entity {

    GamePanel gp;

    public OBJ_PowerGloves(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Power Gloves";
        down1 = setup("/objects/PowerGlove", 1 ,1);
        down2 = setup("/objects/PowerGlove", 2 ,2);
        type = 9;
        description = "[ Krafthandschuhe ]\nMit ihnen kannst du schwere\nBloecke verschieben.";
    }
}
