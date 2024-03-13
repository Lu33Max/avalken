package environment;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.Serializable;

public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;
    Lighting lighting2;

    //// KONSTRUKTOR ////
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    //// SETUP ////
    public void setup() {
        lighting = new Lighting(gp, gp.getTILE_SIZE() * 3);
        lighting2 = new Lighting(gp, gp.getTILE_SIZE() * 10);
    }

    //// ZEICHNEN ////
    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
    public void draw2(Graphics2D g2) {
        lighting2.draw(g2);
    }

}
