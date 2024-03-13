package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bomb extends Entity {

    GamePanel gp;

    public OBJ_Bomb(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Bomb";
        down1 = setup("/objects/bomb", 1 ,1);
        down2 = setup("/objects/bomb", 2 ,2);
        type = 9;
        description = "[ Bombe ]\nKann Felsen in deinem Weg\nzerstoeren.";
        useCost = 1;
        price = 5;
        stackable = true;

        explodingBomb = new OBJ_BombExploding(gp);
        bombActive = false;
    }

    public void use(Entity user) {
        if(explodingBomb.getBombCounter() == 0 && !bombActive && check(user)) {
            explodingBomb.setWorldX(user.getWorldX());
            explodingBomb.setWorldY(user.getWorldY());

            switch (user.getDirection()) {
                case "up" -> explodingBomb.setWorldY(user.getWorldY() - gp.getTILE_SIZE());
                case "down" -> explodingBomb.setWorldY(user.getWorldY() + gp.getTILE_SIZE());
                case "left" -> explodingBomb.setWorldX(user.getWorldX() - gp.getTILE_SIZE());
                case "right" -> explodingBomb.setWorldX(user.getWorldX() + gp.getTILE_SIZE());
            }

            for(int i = 0; i < gp.getObj()[1].length; i++) {
                if(gp.getObj()[gp.getCurrentMap()][i] == null) {
                    gp.setObj(gp.getCurrentMap(), i, explodingBomb);
                    explodingBomb.place(i);
                    explodingBomb.setBombActive(true);
                    subtract(user);
                    break;
                }
            }
        }
    }

    public boolean check(Entity user) {
        return user.getCurrentBomb() >= useCost;
    }
    public void subtract(Entity user) { user.setCurrentBomb(user.getCurrentBomb() - useCost); }
}
