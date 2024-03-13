package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        name = "Chest";
        type = 8;
        collision = true;

        image = setup("/objects/chest", 1, 1);
        image2 = setup("/objects/chest_opened", 1, 1);
        down1 = image;

        ////////////////////////////// ADJUST TO ACTUAL SIZE
        solidArea.x = 0;
        solidArea.y = (gp.getTILE_SIZE() / 3);
        solidArea.width = gp.getTILE_SIZE();
        solidArea.height = (gp.getTILE_SIZE() / 3) * 2;
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;
    }

    public void interact() {
        gp.setGameState(gp.DIALOGUE_STATE);

        if(!opened) {
            gp.playSoundEffect(3);

            if(loot.getName().equals("Bomb")) gp.getPlayer1().setBombObtained(true);
            if(loot.getName().equals("Bow")) gp.getPlayer1().setBowObtained(true);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You opened the chest and find a " + loot.getName() + "!");

            if(!gp.getPlayer1().canObtainItem(loot)) {
                stringBuilder.append("\n... But you cannot carry any more!");
            } else {
                stringBuilder.append("\nYou obtained the " + loot.getName() + "!");
                down1 = image2;
                opened = true;
            }

            gp.getUi().currentDialogue = stringBuilder.toString();

        } else {
            gp.getUi().currentDialogue = "It's empty.";
        }
    }
}
