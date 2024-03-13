package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.Color;

public class IT_DryTree extends InteractiveTile{

    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "DryTree";

        down1 = setup("/interactiveTiles/drytree", 1, 1);
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if(entity.getName().equals("Lantern")) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSoundEffect(11);
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }

    public Color getParticleColor() {
        return new Color(150, 50, 30);
    }
    public int getParticleSpeed() {
        return 1;
    }

    public int getMaxLife() {
        return 20;
    }

}
