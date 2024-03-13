package environment;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;

    //// KONSTRUKTOR ////
    public Lighting(GamePanel gp, int circleSize) {
        this.gp = gp;

        // Erzeugt ein neues BufferedImage in Größe des Bildschirms
        darknessFilter = new BufferedImage(gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        // Erzeugt eine neue Area in Größe des Bildschirms
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT()));

        // Der Mittelpunkt des Lichtkreises wird festgelegt
        int centerX = gp.getSCREEN_WIDTH() /2;
        int centerY = gp.getSCREEN_HEIGHT() /2;

        // x- und y-Position der oberen linken Ecke des Kreises werden bestimmt
        double x = centerX - (circleSize / 2F);
        double y = centerY - (circleSize / 2F);

        // Erstellt die Form des Lichtkreises
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // Erstellt eine Area aus der Form des Lichtkreises
        Area lightArea = new Area(circleShape);

        // Zieht die Area des Lichtkreises von der des Bildschirms ab
        screenArea.subtract(lightArea);

        // Erzeugt Abstufungseffekt am Rand des Lichtkreises
        Color[] color = new Color[5];
        float[] fraction = new float[5];

        for(int i = 0; i < color.length; i++) {
            fraction[i] = i * 0.25F;
            color[i] = new Color(0, 0, 0, i * 62);
        }

        // Erstellt einen neuen RadialGradientPaint mit den vorherigen Einstellungen
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, circleSize / 2F, fraction, color);

        g2.setPaint(gPaint);
        g2.fill(lightArea);
        g2.fill(screenArea);
        g2.dispose();
    }
    public void draw (Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
