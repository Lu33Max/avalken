package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;

    int screenX;
    int screenY;
    int playerNumber;

    boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed, item1KeyPressed, item2KeyPressed;

    // CONSTRUCTOR
    public Player(GamePanel gp, KeyHandler keyHandler, int playerNumber) {

        super(gp);
        this.keyHandler = keyHandler;

        this.playerNumber = playerNumber;

        solidArea = new Rectangle(gp.getTILE_SIZE() / 4, gp.getTILE_SIZE() / 2, gp.getTILE_SIZE() /2, gp.getTILE_SIZE() /2 -1);
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;

        if(playerNumber == 1) {
            getPlayer1Image();
            getPlayer1AttackImage();
        } else {
            getPlayer2Image();
            getPlayer2AttackImage();
        }
        setDefaultValues1P();
        setItems1P();
    }

    // RESET PLAYER VALUES
    public void setDefaultValues1P() {
        if(playerNumber == 1) {
            worldX = gp.getTILE_SIZE() * 37;
            worldY = gp.getTILE_SIZE() * 17;
        } else {
            worldX = 0;
            worldY = 0;
        }
        defaultSpeed = gp.getTILE_SIZE() / 12;
        speed = defaultSpeed;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        currentLife = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        money = 50;
        invincible = false;
        currentWeapon = null;
        currentShield = null;
        currentItem1 = null;
        currentItem2 = null;
        attack = getAttack();
        defense = getDefense();
        projectile = new OBJ_Arrow(gp);

        currentArrow = 10;
        currentBomb = 10;
    }
    public void setDefaultValues2P() {
        if(playerNumber == 1) {
            worldX = gp.getTILE_SIZE() * 18;
            worldY = gp.getTILE_SIZE() * 43;
        } else {
            worldX = gp.getTILE_SIZE() * 25;
            worldY = gp.getTILE_SIZE() * 43;
        }
        defaultSpeed = gp.getTILE_SIZE() / 12;
        speed = defaultSpeed;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 20;
        currentLife = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        money = 0;
        invincible = false;
        currentWeapon = new OBJ_BasicSword(gp);
        currentShield = new OBJ_WoodenShield(gp);
        if(playerNumber == 1) {
            currentItem1 = new OBJ_Bomb(gp);
            currentItem2 = new OBJ_Boots(gp);
        } else {
            currentItem1 = new OBJ_Bow(gp);
            currentItem2 = new OBJ_PowerGloves(gp);
        }
        attack = getAttack();
        defense = getDefense();
        projectile = new OBJ_Arrow(gp);

        currentArrow = 10;
        currentBomb = 10;
    }
    public void setDefaultPosition1P() {
        if(playerNumber == 1) {
            worldX = gp.getTILE_SIZE() * 37;
            worldY = gp.getTILE_SIZE() * 17;
        } else {
           worldX = 0;
           worldY = 0;
        }
        gp.setCurrentMap(0);
        direction = "down";
    }
    public void restoreLife() {
        currentLife = maxLife;
        invincible = false;
    }
    public void setItems1P() {
        inventory.clear();
        inventory.add(new OBJ_BasicSword(gp));
        inventory.add(new OBJ_WoodenShield(gp));
    }

    // ATTACK AND DEFENSE VALUES
    public int getAttack(){
        if(currentWeapon != null) {
            attackArea = currentWeapon.attackArea;
            return strength * currentWeapon.attackValue;
        } else {
            return 0;
        }
    }
    public int getDefense() {
        if(currentShield != null) {
            return dexterity * currentShield.defenseValue;
        } else {
            return 0;
        }
    }
    public int getSpeed() {
        if(currentItem1 != null && currentItem1.name.equals("Boots") || currentItem2 != null && currentItem2.name.equals("Boots")) return speed + gp.getTILE_SIZE() / 24;
        else return speed;
    }

    // LOAD PLAYER SPRITES
    public void getPlayer1Image() {
        up1 = setup("/player/player1_up1", 1, 1);
        up2 = setup("/player/player1_up2", 1, 1);
        down1 = setup("/player/player1_down1", 1, 1);
        down2 = setup("/player/player1_down2", 1, 1);
        left1 = setup("/player/player1_left1", 1, 1);
        left2 = setup("/player/player1_left2", 1, 1);
        right1 = setup("/player/player1_right1", 1, 1);
        right2 = setup("/player/player1_right2", 1, 1);
    }
    public void getPlayer2Image() {
        up1 = setup("/player/player2_up1", 1, 1);
        up2 = setup("/player/player2_up2", 1, 1);
        down1 = setup("/player/player2_down1", 1, 1);
        down2 = setup("/player/player2_down2", 1, 1);
        left1 = setup("/player/player2_left1", 1, 1);
        left2 = setup("/player/player2_left2", 1, 1);
        right1 = setup("/player/player2_right1", 1, 1);
        right2 = setup("/player/player2_right2", 1, 1);
    }
    public void getPlayer1AttackImage() {
            atkUp1 = setup("/player/player1_attacking_up1", 2, 2);
            atkUp2 = setup("/player/player1_attacking_up2", 2, 2);
            atkUp3 = setup("/player/player1_attacking_up3", 2, 2);
            atkDown1 = setup("/player/player1_attacking_down1", 2, 2);
            atkDown2 = setup("/player/player1_attacking_down2", 2, 2);
            atkDown3 = setup("/player/player1_attacking_down3", 2, 2);
            atkLeft1 = setup("/player/player1_attacking_left1", 2, 2);
            atkLeft2 = setup("/player/player1_attacking_left2", 2, 2);
            atkLeft3 = setup("/player/player1_attacking_left3", 2, 2);
            atkRight1 = setup("/player/player1_attacking_right1", 2, 2);
            atkRight2 = setup("/player/player1_attacking_right2", 2, 2);
            atkRight3 = setup("/player/player1_attacking_right3", 2, 2);
    }
    public void getPlayer2AttackImage() {
        atkUp1 = setup("/player/player2_attacking_up1", 2, 2);
        atkUp2 = setup("/player/player2_attacking_up2", 2, 2);
        atkUp3 = setup("/player/player2_attacking_up3", 2, 2);
        atkDown1 = setup("/player/player2_attacking_down1", 2, 2);
        atkDown2 = setup("/player/player2_attacking_down2", 2, 2);
        atkDown3 = setup("/player/player2_attacking_down3", 2, 2);
        atkLeft1 = setup("/player/player2_attacking_left1", 2, 2);
        atkLeft2 = setup("/player/player2_attacking_left2", 2, 2);
        atkLeft3 = setup("/player/player2_attacking_left3", 2, 2);
        atkRight1 = setup("/player/player2_attacking_right1", 2, 2);
        atkRight2 = setup("/player/player2_attacking_right2", 2, 2);
        atkRight3 = setup("/player/player2_attacking_right3", 2, 2);
    }

    public void update() {
        if(attacking) {
            attacking();

        } else if(upPressed || downPressed || leftPressed || rightPressed) {

            // SET MOVEMENT DIRECTION
            if(upPressed) { direction = "up"; }
            if(downPressed) { direction = "down"; }
            if(leftPressed) { direction = "left"; }
            if(rightPressed) { direction = "right"; }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.getCollisionCheck().checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.getCollisionCheck().checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.getCollisionCheck().checkEntity(this, gp.getNpc());
            interactNPC(npcIndex);

            // CHECK ENEMY COLLISION
            int enemyIndex = gp.getCollisionCheck().checkEntity(this, gp.getEnm());
            hitByEnemy(enemyIndex);

            //CHECK EVENT
            gp.getEventHandler().checkEvent(this);

            // CHECK INTERACTIVE TILE COLLISION
            gp.getCollisionCheck().checkEntity(this, gp.getiTile());

            // COLLISION IS FALSE
            if(!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= getSpeed();
                    case "down" -> worldY += getSpeed();
                    case "left" -> worldX -= getSpeed();
                    case "right" -> worldX += getSpeed();
                }
            }

            // SPRITE CHANGER
            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) { spriteNum = 2; }
                else if (spriteNum == 2) { spriteNum = 1; }
                spriteCounter = 0;
            }
        } else {
            idleCounter++;
            if(idleCounter == 20) {
                spriteNum = 1;
                idleCounter = 0;
            }
        }

        // USE ITEMS
        if(item1KeyPressed && currentItem1 != null) currentItem1.use(this);
        if(item2KeyPressed && currentItem2 != null) currentItem2.use(this);

        // ATTACK
        if(attackPressed && currentWeapon != null) {
            attacking = true;
        }

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter == gp.getFPS()) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotLockCounter < 90) {
            shotLockCounter++;
        }

        if(currentLife <= 0) {
            gp.setGameState(gp.GAME_OVER_STATE);
            gp.playSoundEffect(12);
        }

        setScreenCoordinates();
    }
    public void setScreenCoordinates() {
        if(gp.getPlayerCount() == 1) {
            screenX = gp.getSCREEN_WIDTH() / 2 - (gp.getTILE_SIZE() / 2);
            screenY = gp.getSCREEN_HEIGHT() / 2 - (gp.getTILE_SIZE() / 2);
        } else {
            if(playerNumber == 1) {
                screenX = (gp.getSCREEN_WIDTH() / 2) + ((this.worldX - gp.getPlayer2().worldX) /2) - (gp.getTILE_SIZE() / 2);
                screenY = (gp.getSCREEN_HEIGHT() / 2) + ((this.worldY - gp.getPlayer2().worldY) /2) - (gp.getTILE_SIZE() / 2);
            }
            if(playerNumber == 2) {
                screenX = (gp.getSCREEN_WIDTH() / 2) + ((this.worldX - gp.getPlayer1().worldX) /2) - (gp.getTILE_SIZE() / 2);
                screenY = (gp.getSCREEN_HEIGHT() / 2) + ((this.worldY - gp.getPlayer1().worldY) /2) - (gp.getTILE_SIZE() / 2);
            }
        }
    }

    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 3) {spriteNum = 1;}
        if(spriteCounter > 3 && spriteCounter <= 6) {spriteNum = 2;}
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 3;

            // SAVE CURRENT PLAYER DATA
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST PLAYER POSITION FOR ATTACKAREA
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // CHECK ENEMY COLLISION
            int enemyIndex = gp.getCollisionCheck().checkEntity(this, gp.getEnm());
            damageEnemy(enemyIndex, attack, currentWeapon.knockBackPower);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.getCollisionCheck().checkEntity(this, gp.getiTile());
            interactTile(iTileIndex, currentWeapon);

            // CHECK PROJECTILE COLLISION
            int projectileIndex = gp.getCollisionCheck().checkEntity(this, gp.getProjectileList());
            damageProjectile(projectileIndex);

            // RESTORING ORIGINAL POSITION
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    //// COLLISION REACTIONS ////
    public void pickUpObject(int index) {
        if(index != 999) {
            if(gp.getObj()[gp.getCurrentMap()][index].type == 7) {
                gp.getObj()[gp.getCurrentMap()][index].consume(this);
                gp.setObj(gp.getCurrentMap(), index, null);

            } else if(gp.getObj()[gp.getCurrentMap()][index].type == 8) {
                if(keyHandler.isEnterPressed()) {
                    gp.getObj()[gp.getCurrentMap()][index].interact();
                }

            } else if(gp.getObj()[gp.getCurrentMap()][index].type == 3 ||  gp.getObj()[gp.getCurrentMap()][index].type == 5 || gp.getObj()[gp.getCurrentMap()][index].type == 6 || gp.getObj()[gp.getCurrentMap()][index].type == 9) {
                // INVENTORY ITEMS
                String text;

                if(canObtainItem(gp.getObj()[gp.getCurrentMap()][index])) {
                    gp.playSoundEffect(1);
                    text = "Got a " + gp.getObj()[gp.getCurrentMap()][index].name + "!";
                    gp.setObj(gp.getCurrentMap(), index, null);

                } else {
                    text = "Your inventory is full!";
                }
                gp.getUi().addMessage(text);
            }
        }
    }
    public void interactNPC(int index) {
        if(index != 999) {
            if(keyHandler.isEnterPressed()) {
                gp.setGameState(gp.DIALOGUE_STATE);
                gp.getNpc()[gp.getCurrentMap()][index].speak(this);
            }
        }
    }
    public void hitByEnemy(int index) {
        if(index != 999) {
            if(!invincible && !gp.getEnm()[gp.getCurrentMap()][index].dying) {
                gp.playSoundEffect(6);

                int damage = gp.getEnm()[gp.getCurrentMap()][index].attack - defense;
                if(damage < 0) damage = 0;

                currentLife -= damage;
                invincible = true;
            }
        }
    }
    public void damageEnemy (int index, int attack, int knockBackPower) {
        if(index != 999) {
            if(!gp.getEnm()[gp.getCurrentMap()][index].invincible) {
                gp.playSoundEffect(5);

                if(knockBackPower > 0) {
                    knockBack(gp.getEnm()[gp.getCurrentMap()][index], knockBackPower);
                }

                int damage = attack - gp.getEnm()[gp.getCurrentMap()][index].defense;
                if(damage < 0) damage = 0;

                gp.getEnm()[gp.getCurrentMap()][index].currentLife -= damage;
                gp.getUi().addMessage(damage + " damage");

                gp.getEnm()[gp.getCurrentMap()][index].invincible = true;
                gp.getEnm()[gp.getCurrentMap()][index].damageReaction(playerNumber);

                if(gp.getEnm()[gp.getCurrentMap()][index].currentLife <= 0) {
                    gp.getEnm()[gp.getCurrentMap()][index].dying = true;
                    gp.getUi().addMessage(gp.getEnm()[gp.getCurrentMap()][index].name + " defeated!");
                    gp.getUi().addMessage(gp.getEnm()[gp.getCurrentMap()][index].exp + " Exp!");
                    exp += gp.getEnm()[gp.getCurrentMap()][index].exp;

                    if(gp.getPlayerCount() == 1) {
                        checkLevelUp();
                    }
                }
            }
        }
    }
    public void damageProjectile(int i) {
        if(i != 999) {
            Entity projectile = gp.getProjectileList()[gp.getCurrentMap()][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }
    public void interactTile(int index, Entity item) {
        if(index != 999) {
            // DESTROY TILE
            if(gp.getiTile()[gp.getCurrentMap()][index].destructible && gp.getiTile()[gp.getCurrentMap()][index].isCorrectItem(item)) {
                gp.getiTile()[gp.getCurrentMap()][index].playSE();
                generateParticle(gp.getiTile()[gp.getCurrentMap()][index], gp.getiTile()[gp.getCurrentMap()][index]);
                gp.getiTile()[gp.getCurrentMap()][index] = gp.getiTile()[gp.getCurrentMap()][index].getDestroyedForm();
            }
        }
    }
    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed = knockBackPower;
        entity.knockBack = true;
        entity.knockBackUser = this;
    }

    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            exp -= nextLevelExp;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSoundEffect(4);
            gp.setGameState(gp.DIALOGUE_STATE);
            gp.getUi().currentDialogue = "Level up! \n You are level " + level + " now. Your stats have increased.";
        }
    }

    //// ITEM STUFF ////
    public void selectItem() {
        int itemIndex = gp.getUi().getItemIndex(gp.getUi().playerSlotCol, gp.getUi().playerSlotRow);
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == 3) {
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if(selectedItem.type == 5) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == 6) {
                if(selectedItem.consume(this)) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public void selectEquippable1() {
        int itemIndex = gp.getUi().getItemIndex(gp.getUi().playerSlotCol, gp.getUi().playerSlotRow);
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == 9) {
                if(selectedItem == currentItem2) {
                    currentItem2 = null;
                }
                currentItem1 = selectedItem;
            }
        }
    }
    public void selectEquippable2() {
        int itemIndex = gp.getUi().getItemIndex(gp.getUi().playerSlotCol, gp.getUi().playerSlotRow);
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == 9) {
                if(selectedItem == currentItem1) {
                    currentItem1 = null;
                }
                currentItem2 = selectedItem;
            }
        }
    }
    public int findItemInInventory(String itemName) {
        int itemIndex = 999;

        if(itemName.equals("Arrow")) itemName = "Bow";

        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        // CHECK IF ITEM IS STACKABLE
        if(item.stackable) {
            int index = findItemInInventory(item.name);
            if(index != 999) {
                if(item.name.equals("Arrow")) {
                    currentArrow += 5;
                } else if(item.name.equals("Bomb")) {
                    currentBomb += 5;
                } else {
                    inventory.get(index).amount++;
                }
                canObtain = true;
            } else {
                if(inventory.size() != INVENTORY_SLOTS) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        } else {
            if(inventory.size() != INVENTORY_SLOTS) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    //// PLAYER ANIMATION ////
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX, tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if(attacking) {
                    tempScreenY -= gp.getTILE_SIZE();
                    if(spriteNum == 1) image = atkUp1;
                    if(spriteNum == 2) image = atkUp2;
                    if(spriteNum == 3) image = atkUp3;
                } else {
                    if(spriteNum == 1) image = up1;
                    if(spriteNum == 2) image = up2;
                }
            }
            case "down" -> {
                if(attacking) {
                    tempScreenX -= gp.getTILE_SIZE();
                    if(spriteNum == 1) image = atkDown1;
                    if(spriteNum == 2) image = atkDown2;
                    if(spriteNum == 3) image = atkDown3;
                } else {
                    if(spriteNum == 1) image = down1;
                    if(spriteNum == 2) image = down2;
                }
            }
            case "left" -> {
                if(attacking) {
                    tempScreenX -= gp.getTILE_SIZE();
                    tempScreenY -= gp.getTILE_SIZE();
                    if(spriteNum == 1) image = atkLeft1;
                    if(spriteNum == 2) image = atkLeft2;
                    if(spriteNum == 3) image = atkLeft3;
                } else {
                    if(spriteNum == 1) image = left1;
                    if(spriteNum == 2) image = left2;
                }
            }
            case "right" -> {
                if(attacking) {
                    tempScreenY -= gp.getTILE_SIZE();
                    if(spriteNum == 1) image = atkRight1;
                    if(spriteNum == 2) image = atkRight2;
                    if(spriteNum == 3) image = atkRight3;
                } else {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }
        }

        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    //// GETTER ////
    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }

    //// SETTER ////
    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }
    public void setAttackPressed(boolean attackPressed) { this.attackPressed = attackPressed; }
    public void setItem1KeyPressed(boolean item1KeyPressed) { this.item1KeyPressed = item1KeyPressed; }
    public void setItem2KeyPressed(boolean item2KeyPressed) { this.item2KeyPressed = item2KeyPressed; }
}
