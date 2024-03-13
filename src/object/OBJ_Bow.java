package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

    GamePanel gp;

    public OBJ_Bow(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Bow";
        down1 = setup("/objects/bow", 1 ,1);
        down2 = setup("/objects/bow", 2 ,2);
        type = 9;
        description = "[ Bogen ]\nmit ihm kannst du Pfeile schiessen.";
        useCost = 1;

        projectile = new OBJ_Arrow(gp);
    }

    public void use(Entity user) {
        if(!projectile.isAlive() && user.getShotLockCounter() == 90 && check(user)) {
            projectile.set(user.getWorldX(), user.getWorldY(), user.getDirection(), true, user);

            for(int i = 0; i < gp.getProjectileList()[0].length; i++) {
                if(gp.getProjectileList()[gp.getCurrentMap()][i] == null) {
                    gp.getProjectileList()[gp.getCurrentMap()][i] = projectile;
                    break;
                }
            }
            subtract(user);
            user.setShotLockCounter(0);
        }
    }

    public boolean check(Entity user) { return user.getCurrentArrow() >= useCost; }
    public void subtract(Entity user) { user.setCurrentArrow(user.getCurrentArrow() - useCost); }
}
