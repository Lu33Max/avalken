package map;

import main.GamePanel;

import java.util.ArrayList;

public class MapManager {

    GamePanel gp;
    ArrayList<Map> mapList;

    public MapManager(GamePanel gp) {
        this.gp = gp;

        mapList = new ArrayList<>();
    }

    public void setUp() {
        mapList.add(new Map(gp, "/maps/overworld.txt", 96, 72, 0, false));
        mapList.add(new Map(gp, "/maps/interior01.txt", 40, 28, 0, false));

        // DUNGEON 1
        gp.setDungeon1Maps(mapList.size());
        mapList.add(new Map(gp, "/maps/dungeon2-1.txt", 40, 24, 1, true));
        mapList.add(new Map(gp, "/maps/dungeon2-2.txt", 70, 24, 0, false));
        mapList.add(new Map(gp, "/maps/dungeon2-3.txt", 40, 24, 1, false));
        mapList.add(new Map(gp, "/maps/dungeon2-4.txt", 40, 24, 1, false));
        mapList.add(new Map(gp, "/maps/dungeon2-5.txt", 40, 24, 0, false));
        mapList.add(new Map(gp, "/maps/dungeon2-6.txt", 40, 24, 1, false));
        mapList.add(new Map(gp, "/maps/dungeon2-7.txt", 40, 24, 0, false));
        mapList.add(new Map(gp, "/maps/dungeon2-8.txt", 40, 24, 3, false));
        mapList.add(new Map(gp, "/maps/dungeon2-9.txt", 72, 32, 1, false));
        mapList.add(new Map(gp, "/maps/dungeon2-10.txt", 40, 24, 0, false));
        mapList.add(new Map(gp, "/maps/dungeon2-11.txt", 40, 32, 2, true));
        mapList.add(new Map(gp, "/maps/dungeon2-12.txt", 40, 24, 2, true));
        mapList.add(new Map(gp, "/maps/dungeon2-13.txt", 40, 24, 2, true));
        mapList.add(new Map(gp, "/maps/dungeon2-14.txt", 40, 24, 0, true));
        mapList.add(new Map(gp, "/maps/dungeon2-15.txt", 40, 24, 1, true));
        mapList.add(new Map(gp, "/maps/dungeon2-16.txt", 40, 24, 0, true));
        mapList.add(new Map(gp, "/maps/dungeon2-17.txt", 40, 24, 2, true));

        // DUNGEON 2
        gp.setDungeon2Maps(mapList.size());
        mapList.add(new Map(gp, "/maps/dungeon1-1.txt", 44, 54, 3, false));
    }

    public ArrayList<Map> getMapList() {
        return mapList;
    }
}
