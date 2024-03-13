package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_CrystalRed extends InteractiveTile {

    GamePanel gp;
    int counter;
    boolean lock;

    public IT_CrystalRed(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.getTILE_SIZE() * col;
        this.worldY = gp.getTILE_SIZE() * row;
        name = "CrystalRed";

        down1 = setup("/interactiveTiles/CrystalRed", 1, 1);
        destructible = true;
    }

    public void update(){
        if(counter < 40) {
            counter++;
        } else {
            lock = true;
        }
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if(lock) {
            if (entity.getName().equals("ExplodingBomb") || entity.getName().equals("Arrow") || entity.getName().equals("Basic Sword")) {
                isCorrectItem = true;
                counter = 0;
                lock = false;

                for(int i = 0; i < gp.getiTile()[0].length; i++) {
                    if(gp.getiTile()[gp.getCurrentMap()][i] != null) {
                        if(gp.getiTile()[gp.getCurrentMap()][i].name.equals("BlockadeUp") || gp.getiTile()[gp.getCurrentMap()][i].name.equals("BlockadeDown")) {
                            gp.getiTile()[gp.getCurrentMap()][i] = gp.getiTile()[gp.getCurrentMap()][i].getDestroyedForm();
                        }
                    }
                }
            }
        }
        return isCorrectItem;
    }

    public InteractiveTile getDestroyedForm() {
        return new IT_CrystalBlue(gp, worldX / gp.getTILE_SIZE(), worldY / gp.getTILE_SIZE());
    }
}
