package hud;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HUD_ItemFrames extends SuperHUD {

    public HUD_ItemFrames(GamePanel gp) {
        super(gp);

        name = "ItemFrames";
        image = new BufferedImage[5];

        try {
            image[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/1player_itemframe1.png")));
            image[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/1player_itemframe2.png")));
            image[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/2player_itemframe2.png")));
            image[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/2player_itemframe3.png")));
            image[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/hud/2player_itemframe4.png")));

            for (int i = 0; i < image.length; i++) {
                image[i] = utilityTool.scaleImage(image[i], (int) (gp.getHUD_SIZE() * 4), (int) (gp.getHUD_SIZE() * 4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
