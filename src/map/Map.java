package map;

import entity.Entity;
import main.GamePanel;
import object.*;
import tile_interactive.IT_DestructiblePlate;
import tile_interactive.IT_Stairs;
import tile_interactive.InteractiveTile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {

    int maxCol, maxRow, clearCondition;
    boolean dark, cleared;
    boolean activated = false;
    String filePath;
    int[][] mapTileNum;
    GamePanel gp;

    public Map(GamePanel gp, String filePath, int maxCol, int maxRow, int clearCondition, boolean dark) {
        this.gp = gp;

        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.filePath = filePath;
        this.clearCondition = clearCondition;
        this.dark = dark;

        cleared = false;
        mapTileNum = new int[maxCol][maxRow];
        loadMap(filePath, maxCol, maxRow);
    }

    public void loadMap(String filePath, int maxCol, int maxRow) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < maxCol && row < maxRow) {
                String line = bufferedReader.readLine();

                while (col < maxCol) {
                    String[] numbers = line.split("\t");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == maxCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update() {
        switch (clearCondition) {
            // DEFEAT ALL ENEMIES
            case 1 -> {
                for (int i = 0; i < gp.getEnm()[0].length; i++) {
                    if (gp.getEnm()[gp.getCurrentMap()][i] != null) {
                        cleared = false;
                        break;
                    }
                    if (i == gp.getEnm()[0].length - 1) {
                        cleared = true;
                    }
                }
            }

            // LIGHT ALL LAMPS
            case 2 -> {
                for (int i = 0; i < gp.getiTile()[0].length; i++) {
                    if (gp.getiTile()[gp.getCurrentMap()][i] != null) {
                        if (gp.getiTile()[gp.getCurrentMap()][i].getName().equals("LampOff")) {
                            cleared = false;
                            break;
                        }
                    }
                    if (i == gp.getiTile()[0].length - 1) {
                        cleared = true;
                        dark = false;
                    }
                }
            }

            // MOVE ALL BLOCKS
            case 3 -> {
                for (int i = 0; i < gp.getiTile()[0].length; i++) {
                    if (gp.getiTile()[gp.getCurrentMap()][i] != null) {
                        if (gp.getiTile()[gp.getCurrentMap()][i].getName().equals("Movable Block")) {
                            cleared = false;
                            break;
                        }
                    }
                    if (i == gp.getiTile()[0].length - 1) {
                        cleared = true;
                    }
                }
            }
        }

        if(cleared && !activated){
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +5 || gp.getCurrentMap() == gp.getDungeon1Maps() +10 || gp.getCurrentMap() == gp.getDungeon1Maps() +11 || gp.getCurrentMap() == gp.getDungeon1Maps() +14
                || gp.getCurrentMap() == gp.getDungeon2Maps()) {
                openDoors();
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +16) {
                spawnItem(new OBJ_Chest(gp, new OBJ_RedPotion(gp)), 17, 10);
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +12) {
                gp.getiTile()[gp.getCurrentMap()][2] = null;
                gp.getiTile()[gp.getCurrentMap()][3] = null;
                spawnITile(new IT_DestructiblePlate(gp, 19, 12));
                spawnITile(new IT_DestructiblePlate(gp, 20, 12));
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +8) {
                spawnItem(new OBJ_Key(gp), 29, 10);
                spawnItem(new OBJ_Key(gp), 30, 10);
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +7) {
                spawnITile(new IT_Stairs(gp, 23, 9, false, gp.getDungeon1Maps() +13, 21, 14));
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +3) {
                spawnItem(new OBJ_Chest(gp, new OBJ_BossKey(gp)), 23, 10);
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps() +2) {
                spawnITile(new IT_Stairs(gp, 23, 9, false, gp.getDungeon1Maps() +6, 22, 9));
            }
            if(gp.getCurrentMap() == gp.getDungeon1Maps()) {
                spawnItem(new OBJ_Chest(gp, new OBJ_Bow(gp)), 19, 12);
            }
            activated = true;
        }
    }

    public void openDoors() {
        for(int i = 0; i < gp.getiTile()[0].length; i++) {
            if(gp.getiTile()[gp.getCurrentMap()][i] != null) {
                if(gp.getiTile()[gp.getCurrentMap()][i].getName().equals("ClosedDoor")) {
                    gp.getiTile()[gp.getCurrentMap()][i] = null;
                }
            }
        }
    }
    public void spawnItem(Entity item, int tileX, int tileY) {
        for(int i = 0; i < gp.getObj()[0].length; i++) {
            if(gp.getObj()[gp.getCurrentMap()][i] == null) {
                gp.setObj(gp.getCurrentMap(), i, item);
                gp.getObj()[gp.getCurrentMap()][i].setWorldX(gp.getTILE_SIZE() * tileX);
                gp.getObj()[gp.getCurrentMap()][i].setWorldY(gp.getTILE_SIZE() * tileY);
                break;
            }
        }
    }
    public void spawnITile(InteractiveTile iTile) {
        for(int i = 0; i < gp.getiTile()[0].length; i++) {
            if(gp.getiTile()[gp.getCurrentMap()][i] == null) {
                gp.getiTile()[gp.getCurrentMap()][i] = iTile;
                break;
            }
        }
    }

    //// GETTER ////
    public int[][] getMapTileNum() { return mapTileNum; }
    public int getMaxCol() { return maxCol; }
    public int getMaxRow() { return maxRow; }
    public boolean getDark() { return dark; }
}
