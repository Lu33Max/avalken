package hud;

import main.GamePanel;
import main.UtilityTool;

import java.awt.image.BufferedImage;

public class SuperHUD {

    GamePanel gp;
    BufferedImage[] image;
    String name;
    UtilityTool utilityTool = new UtilityTool();

    public SuperHUD(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage[] getImage() { return image; }
}
