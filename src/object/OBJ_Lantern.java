package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

    GamePanel gp;

    public OBJ_Lantern(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Lantern";
        down1 = setup("/objects/lantern", 1 ,1);
        down2 = setup("/objects/lantern", 2 ,2);
        type = 9;
        description = "[ Laterne ]\nKann dunkle Orte erhellen und\ntrockene Baeume verbrennen.";
        price = 30;
    }

    public void use(Entity user){
        int iTileIndex = gp.getCollisionCheck().checkEntity(gp.getPlayer1(), gp.getiTile());
        gp.getPlayer1().interactTile(iTileIndex, this);
    }
}
