package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class OBJ_BombExploding extends Entity{

    GamePanel gp;
    private int index = 0;

    public OBJ_BombExploding(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "ExplodingBomb";
        down1 = setup("/objects/bomb", 1 ,1);
        type = 10;
        attack = 2;
        knockBackPower = 10;
        solidArea = new Rectangle(-gp.getTILE_SIZE() / 2, -gp.getTILE_SIZE() / 2, gp.getTILE_SIZE() * 2, gp.getTILE_SIZE() * 2);

        bombCounter = 0;
        bombActive = false;
    }

    public void place(int i) {
        index = i;
    }

    public void update() {
        if(bombActive) {
            bombCounter++;
        }

        if(bombCounter == 60) {
            gp.setObj(gp.getCurrentMap(), index, null);
            bombActive = false;
            bombCounter = 0;
            explode();
        }
    }

    public void explode() {
        // ENEMY COLLISION
        int enemyIndex = gp.getCollisionCheck().checkEntity(this, gp.getEnm());
        gp.getPlayer1().damageEnemy(enemyIndex, attack, knockBackPower);

        // PLAYER COLLISION
        if(!gp.getPlayer1().isInvincible() && gp.getCollisionCheck().checkPlayer(this, gp.getPlayer1())) {
            damagePlayer(attack, gp.getPlayer1());
        }
        if(!gp.getPlayer2().isInvincible() && gp.getCollisionCheck().checkPlayer(this, gp.getPlayer2())) {
            damagePlayer(attack, gp.getPlayer2());
        }
        // INTERACTIVE TILE COLLISION
        int iTileIndex = gp.getCollisionCheck().checkEntity(this, gp.getiTile());
        gp.getPlayer1().interactTile(iTileIndex, this);
    }
}
