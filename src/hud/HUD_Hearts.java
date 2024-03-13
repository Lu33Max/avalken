package hud;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HUD_Hearts extends SuperHUD {

    public HUD_Hearts(GamePanel gp) {
        super(gp);

        name = "Hearts";
        image = new BufferedImage[5];

        try {
            image[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/emptyheart.png")));
            image[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/halfheart.png")));
            image[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/fullheart.png")));
            image[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/halfheart_blue.png")));
            image[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/fullheart_blue.png")));

            for (int i = 0; i < image.length; i++) {
                image[i] = utilityTool.scaleImage(image[i], gp.getHUD_SIZE(), gp.getHUD_SIZE());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
