package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RedPotion extends Entity {

    GamePanel gp;

    public OBJ_RedPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 6;
        name = "Red Potion";
        value = 6;
        down1 = setup("/objects/RedPotion", 1 ,1);
        down2 = setup("/objects/RedPotion", 2 ,2);
        description = "[ Heiltrank ]\nHeilt dich um " + value/2 + " Herzen.";
        price = 30;
        stackable = true;
    }

    public boolean consume(Entity entity) {
        gp.setGameState(gp.DIALOGUE_STATE);
        gp.getUi().currentDialogue = "Du trinkst den Heiltrank!\nDein Leben wurde um " + value/2 + " Herzen regeneriert.";
        if(entity.getCurrentLife() + value > entity.getMaxLife()) {
            entity.setCurrentLife(entity.getMaxLife());
        } else {
            entity.setCurrentLife(entity.getCurrentLife() + value);
        }
        gp.playSoundEffect(2);
        return true;
    }
}
