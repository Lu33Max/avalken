package entity;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle extends Entity{

    final Color color;
    final int size;
    final int xd;
    int yd;

    //// KONSTRUKTOR ////
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        currentLife = maxLife;
        int offset = (gp.getTILE_SIZE() / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {
        // Das Leben wird jeden Frame verringert
        currentLife--;

        // Ist das Leben im letzten Drittel angekommen, bewegt sich der Partikel schneller nach unten
        if(currentLife < maxLife/3) {
            yd++;
        }

        // Der Partikel bewegt sich mit seiner Geschwindigkeit
        worldX += xd * speed;
        worldY += yd * speed;

        // Der Partikel verschwindet, sobald das Leben auf 0 fällt
        if(currentLife == 0) {
            alive = false;
        }
    }

    public void draw(Graphics2D g2) {
        // Die Bildschirmkoordinaten des Partikels werden ausgerechnet
        int screenX, screenY;
        if(gp.getPlayerCount() == 1) {
            screenX = worldX - gp.getPlayer1().worldX + gp.getPlayer1().screenX;
            screenY = worldY - gp.getPlayer1().worldY + gp.getPlayer1().screenY;
        } else {
            screenX = worldX - gp.getScreenCenterX() + gp.getSCREEN_WIDTH() /2;
            screenY = worldY - gp.getScreenCenterY() + gp.getSCREEN_HEIGHT() /2;
        }

        // Der Partikel wird in der angegebenen Farbe und Größe gezeichnet
        g2.setColor(color);
        g2.fillRect(screenX,screenY, size, size);
    }
}
