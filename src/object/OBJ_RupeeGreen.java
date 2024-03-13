package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RupeeGreen extends Entity {

    GamePanel gp;

    public OBJ_RupeeGreen(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 7;
        name = "Green Rupee";
        value = 1;
        down1 = setup("/objects/RupeeGreen", 1, 1);
    }

    public boolean consume(Entity entity) {
        gp.playSoundEffect(1);
        gp.getUi().addMessage("Rupee +" + value);
        gp.getPlayer1().setMoney(gp.getPlayer1().getMoney() + value);
        return true;
    }
}
