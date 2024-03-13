package entity;

import main.GamePanel;

import java.io.Serializable;

public class Projectile extends Entity implements Serializable {

    Entity user;

    //// KONSTRUKTOR ////
    public Projectile(GamePanel gp) {
        super(gp);
    }

    //// POSITION ////
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        // Setzt Position des Projektils auf die des Spielers
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.currentLife = maxLife;
    }

    public void update() {
        // Falls der Nutzer Spieler 1 ist
        if(user == gp.getPlayer1()) {
            // Teste, ob ein Gegner getroffen wurde
            int enemyIndex = gp.getCollisionCheck().checkEntity(this, gp.getEnm());
            if(enemyIndex != 999) {
                // Füge dem Gegner Schaden zu
                gp.getPlayer1().damageEnemy(enemyIndex, attack, knockBackPower);
                // Erzeuge Partikel
                generateParticle(user.projectile, gp.getEnm()[gp.getCurrentMap()][enemyIndex]);
                // Das Projektil verschwindet
                alive = false;
            }
            // Teste, ob ein Interaktives Tile getroffen wurde
            int iTileIndex = gp.getCollisionCheck().checkEntity(this, gp.getiTile());
            if(iTileIndex != 999) {
                // Spieler 1 interagiert mit diesem
                gp.getPlayer1().interactTile(iTileIndex, this);
                // Das Projektil verschwindet
                alive = false;
            }

        // Falls der Nutzer Spieler 2 ist
        } else if(user == gp.getPlayer2()){
            // Analog Spieler 1
            int enemyIndex = gp.getCollisionCheck().checkEntity(this, gp.getEnm());
            if(enemyIndex != 999) {
                gp.getPlayer2().damageEnemy(enemyIndex, attack, knockBackPower);
                generateParticle(user.projectile, gp.getEnm()[gp.getCurrentMap()][enemyIndex]);
                alive = false;
            }
            int iTileIndex = gp.getCollisionCheck().checkEntity(this, gp.getiTile());
            if(iTileIndex != 999) {
                gp.getPlayer2().interactTile(iTileIndex, this);
                alive = false;
            }

        // Falls der Nutzer kein Spieler ist
        } else {
            // Teste, ob Spieler 1 getroffen wurde
            if(!gp.getPlayer1().invincible && gp.getCollisionCheck().checkPlayer(this, gp.getPlayer1())) {
                // Verursache Schaden beim Spieler
                damagePlayer(attack, gp.getPlayer1());
                // Analog
                generateParticle(user.projectile, gp.getPlayer1());
                alive = false;
            }
            // Teste, ob Spieler 2 getroffen wurde
            if(gp.getPlayerCount() == 2) {
                // Analog
                if (!gp.getPlayer2().invincible && gp.getCollisionCheck().checkPlayer(this, gp.getPlayer2())) {
                    damagePlayer(attack, gp.getPlayer2());
                    generateParticle(user.projectile, gp.getPlayer2());
                    alive = false;
                }
            }
        }

        // Das Projektil bewegt sich mit seiner Geschwindigkeit in seine aktuelle Richtung
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        // Das Leben des Projektils wird verringert. Fällt es auf 0, verschwindet das Projektil.
        currentLife--;
        if(currentLife < 0) {
            alive = false;
        }

        // Alle 12 Frames ändert das Projektil seinen Sprite
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) { spriteNum = 2; }
            else if (spriteNum == 2) { spriteNum = 1; }
            spriteCounter = 0;
        }
    }
}
