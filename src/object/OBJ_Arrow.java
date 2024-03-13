package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.Color;

public class OBJ_Arrow extends Projectile {

    public OBJ_Arrow(GamePanel gp) {
        super(gp);

        name = "Arrow";
        speed = 15;
        maxLife = 80;
        currentLife = maxLife;
        attack = 2;
        useCost = 1;
        knockBackPower = 5;
        type = 9;
        stackable = true;

        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/arrow_up_1", 1, 1);
        up2 = setup("/projectile/arrow_up_1", 1, 1);
        down1 = setup("/projectile/arrow_down_1", 1, 1);
        down2 = setup("/projectile/arrow_down_1", 1, 1);
        left1 = setup("/projectile/arrow_left_1", 1, 1);
        left2 = setup("/projectile/arrow_left_1", 1, 1);
        right1 = setup("/projectile/arrow_right_1", 1, 1);
        right2 = setup("/projectile/arrow_right_1", 1, 1);
    }

    public Color getParticleColor() {
        return new Color(130, 80, 0);
    }
    public int getParticleSpeed() {
        return 2;
    }
    public int getMaxLife() {
        return 10;
    }
}
