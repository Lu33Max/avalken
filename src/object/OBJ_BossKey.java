package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BossKey extends Entity {

    GamePanel gp;

    public OBJ_BossKey(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 9;
        name = "BossKey";
        down1 = setup("/objects/BossKey", 1, 1);
        down2 = setup("/objects/BossKey", 2, 2);
        description = "[ Bossschluessel ]\nOeffnet die Tuer zum Meister\ndieses tempels.";
    }
}
