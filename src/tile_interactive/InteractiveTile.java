package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;
    public int index;
    Rectangle interactionRect;
    String name = "";

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }
    public void playSE() {}
    public InteractiveTile getDestroyedForm() {
        return null;
    }
    public void update() {}

    public String  getName() {
        return name;
    }
}
