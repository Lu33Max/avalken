package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 6;
        name = "Key";
        down1 = setup("/objects/key", 1, 1);
        down2 = setup("/objects/key", 2, 2);
        description = "[ Schluessel ]\nKann Tueren oeffnen.";
        price = 20;
        stackable = true;
    }
}
