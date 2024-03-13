package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RupeeBlue extends Entity {

    GamePanel gp;

    public OBJ_RupeeBlue(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 7;
        name = "Blue Rupee";
        value = 5;
        down1 = setup("/objects/RupeeBlue", 1, 1);
    }

    public boolean consume(Entity entity) {
        gp.playSoundEffect(1);
        gp.getUi().addMessage("Rupee +" + value);
        gp.getPlayer1().setMoney(gp.getPlayer1().getMoney() + value);
        return true;
    }
}
