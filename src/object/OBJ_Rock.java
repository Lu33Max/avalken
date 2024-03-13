package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.Color;

public class OBJ_Rock extends Projectile {

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        name = "Rock";
        speed = 8;
        maxLife = 40;
        currentLife = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/rock_down_1", 1, 1);
        up2 = setup("/projectile/rock_down_1", 1, 1);
        down1 = setup("/projectile/rock_down_1", 1, 1);
        down2 = setup("/projectile/rock_down_1", 1, 1);
        left1 = setup("/projectile/rock_down_1", 1, 1);
        left2 = setup("/projectile/rock_down_1", 1, 1);
        right1 = setup("/projectile/rock_down_1", 1, 1);
        right2 = setup("/projectile/rock_down_1", 1, 1);
    }

    public Color getParticleColor() {
        return new Color(40, 50, 30);
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getMaxLife() {
        return 20;
    }
}
