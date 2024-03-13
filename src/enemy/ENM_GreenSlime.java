package enemy;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class ENM_GreenSlime extends Entity {

    GamePanel gp;

    public ENM_GreenSlime(GamePanel gp, int tileX, int tileY) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = 2;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        currentLife = maxLife;
        attack = 3;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        worldX = tileX * gp.getTILE_SIZE();
        worldY = tileY * gp.getTILE_SIZE();

        solidArea.x = gp.getSCALE();
        solidArea.y = (gp.getTILE_SIZE() * 3) / 8;
        solidArea.width = gp.getTILE_SIZE() - (2 * solidArea.x);
        solidArea.height = gp.getTILE_SIZE() - solidArea.y;
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;

        getImage();
    }

    public void getImage() {
        // Die Sprites des Schleims werden geladen und skaliert
        up1 = setup("/enemies/greenslime_down_1", 1, 1);
        up2 = setup("/enemies/greenslime_down_2", 1, 1);
        right1 = left1 = down1 = up1;
        right2 = left2 = down2 = up2;
    }

    public void update() {
        super.update();

        // Überprüft den Abstand zu Spieler 1
        int x1Distance = Math.abs(worldX - gp.getPlayer1().getWorldX());
        int y1Distance = Math.abs(worldY - gp.getPlayer1().getWorldY());
        int tile1Distance = (x1Distance + y1Distance) / gp.getTILE_SIZE();

        // Überprüft den Abstand zu Spieler 2
        int x2Distance = Math.abs(worldX - gp.getPlayer2().getWorldX());
        int y2Distance = Math.abs(worldY - gp.getPlayer2().getWorldY());
        int tile2Distance = (x2Distance + y2Distance) / gp.getTILE_SIZE();

        // Ist der Abstand zu einem der Spieler geringer als 5 Tiles wird dieser verfolgt, bis er mehr als 10 Tiles entfernt ist
        if(tile1Distance < tile2Distance) {
            onPath2 = false;
            if (!onPath1 && tile1Distance < 5) onPath1 = true;
            if (onPath1 && tile1Distance > 10) onPath1 = false;
        } else {
            onPath1 = false;
            if (!onPath2 && tile2Distance < 5) onPath2 = true;
            if (onPath2 && tile2Distance > 10) onPath2 = false;
        }
    }

    public void setAction() {
        // Test, ob der schleim gerade einen Spieler verfolgt
        if(onPath1 || onPath2) {

            // Der Schleim schießt zufällig und wenn mindestens 90 Frames vergangen sind ein Projektil
            int x = new Random().nextInt(200) + 1;
            if (x > 197 && !projectile.isAlive() && shotLockCounter == 90) {

                // Die Attribute des Projektils werden gesetzt
                projectile.set(worldX, worldY, direction, true, this);

                // Das Projektil wird an der ersten freien Stelle der projectileList hinzugefügt
                for (int i = 0; i < gp.getProjectileList().length; i++) {
                    if (gp.getProjectileList()[gp.getCurrentMap()][i] == null) {
                        gp.getProjectileList()[gp.getCurrentMap()][i] = projectile;
                        break;
                    }
                }

                shotLockCounter = 0;
            }

            // Test, ob der schleim Spieler 1 verfolgt
            if (onPath1) {
                // EndCol und EndRow der Pfadsuche werden auf die Tile-Position von Spieler 1 gesetzt
                int endCol = (gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x) / gp.getTILE_SIZE();
                int endRow = (gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y) / gp.getTILE_SIZE();
                searchPath(endCol, endRow);

            // Test, ob der schleim Spieler 1 verfolgt
            } else if (onPath2) {
                // EndCol und EndRow der Pfadsuche werden auf die Tile-Position von Spieler 2 gesetzt
                int endCol = (gp.getPlayer2().getWorldX() + gp.getPlayer2().getSolidArea().x) / gp.getTILE_SIZE();
                int endRow = (gp.getPlayer2().getWorldY() + gp.getPlayer2().getSolidArea().y) / gp.getTILE_SIZE();
                searchPath(endCol, endRow);
            }

        // Falls der Schleim keinen Spieler verfolgt
        } else {
            actionLock++;

            // Alle 120 Frames wählt der Schleim zufällig eine Richtung in die er sich bewegt
            if (actionLock == 120) {
                Random random = new Random();
                int i = random.nextInt(4);

                switch (i) {
                    case 0 -> direction = "up";
                    case 1 -> direction = "down";
                    case 2 -> direction = "left";
                    case 3 -> direction = "right";
                }

                actionLock = 0;
            }
        }
    }

    public void damageReaction(int playerNum) {
        // Verfolgt einen Spieler, wenn er von ihm getroffen wird
        actionLock = 0;
        if(playerNum == 1) onPath1 = true;
        if(playerNum == 2) onPath2 = true;
    }

    public void checkDrop() {
        // Lässt nach seinem Tod zufällig ein Item fallen
        int i = new Random().nextInt(100) + 1;

        if(i < 20) dropItem(new OBJ_RupeeGreen(gp));
        if(i >= 50 && i < 60) { new OBJ_RupeeBlue(gp); }
        if(i >= 70 && i < 80 && gp.getPlayer1().isBombObtained()) { new OBJ_Bomb(gp); }
        if(i >= 80 && i < 90 && gp.getPlayer1().isBowObtained()) { new OBJ_Arrow(gp); }
    }
}
