package hud;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HUD_LifeText extends SuperHUD {

    public HUD_LifeText(GamePanel gp) {
        super(gp);

        name = "Hearts";
        image = new BufferedImage[1];

        try {
            image[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/lifetext.png")));
            image[0] = utilityTool.scaleImage(image[0], 5 * gp.getHUD_SIZE(), gp.getHUD_SIZE());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
