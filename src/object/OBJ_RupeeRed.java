package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RupeeRed extends Entity {

    GamePanel gp;

    public OBJ_RupeeRed(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 7;
        name = "Red Rupee";
        value = 20;
        down1 = setup("/objects/RupeeRed", 1, 1);
    }

    public boolean consume(Entity entity) {
        gp.playSoundEffect(1);
        gp.getUi().addMessage("Rupee +" + value);
        gp.getPlayer1().setMoney(gp.getPlayer1().getMoney() + value);
        return true;
    }
}
