package entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {
        super(gp);

        speed = 1;
        currentBomb = 5;

        solidArea.x = 0;
        solidArea.y = (gp.getTILE_SIZE() / 3);
        solidArea.width = gp.getTILE_SIZE();
        solidArea.height = (gp.getTILE_SIZE() / 3) * 2;
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;
        direction = "down";

        getImage();
        setDialogue();
        setItems();
    }

    // LOAD NPC SPRITES
    public void getImage() {
        up1 = setup("/npcs/merchant_down_1", 1, 1);
        up2 = setup("/npcs/merchant_down_2", 1, 1);
        down1 = setup("/npcs/merchant_down_1", 1, 1);
        down2 = setup("/npcs/merchant_down_2", 1, 1);
        left1 = setup("/npcs/merchant_down_1", 1, 1);
        left2 = setup("/npcs/merchant_down_2", 1, 1);
        right1 = setup("/npcs/merchant_down_1", 1, 1);
        right2 = setup("/npcs/merchant_down_2", 1, 1);
    }

    public void setDialogue() {
        dialogues[0] = "Heiltraenke, Schilde, Bomben!\nDu willst etwas haben? Es gehoert dir mein Freund, aber\nnur solange du auch genug Rubine hast.";
    }

    public void setItems() {
        inventory.add(new OBJ_RedPotion(gp));
        inventory.add(new OBJ_Bomb(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_BlueShield(gp));
    }

    public void speak(Entity player) {
        super.speak(player);
        gp.setGameState(gp.TRADE_STATE);
        gp.getUi().npc = this;
    }
}
