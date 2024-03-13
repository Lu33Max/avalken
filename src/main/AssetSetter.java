package main;

import enemy.ENM_GreenSlime;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import object.*;
import tile_interactive.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Bomb(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 60); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 17); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeRed(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 20); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 57); i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Boots(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 63); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 36);

        //// DUNGEON 1 ////

        // MAP 2
        mapNum = gp.dungeon1Maps +1; i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 32); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 9); i++;

        // MAP 9
        mapNum = gp.dungeon1Maps +8; i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 28); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 20);

        // MAP 10
        mapNum = gp.dungeon1Maps +9; i = 0;
        gp.obj[mapNum][i] = new OBJ_RupeeRed(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 19); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 12); i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_RedPotion(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 20); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 12); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 19); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 11); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 20); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 11); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 18); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 12); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 21); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 12); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 19); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 13); i++;
        gp.obj[mapNum][i] = new OBJ_RupeeBlue(gp); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 20); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 13);

        // MAP 13
        mapNum = gp.dungeon1Maps +12; i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_PowerGloves(gp)); gp.obj[mapNum][i].setWorldX(gp.getTILE_SIZE() * 19); gp.obj[mapNum][i].setWorldY(gp.getTILE_SIZE() * 10);
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        //MAP 0
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].setWorldX(gp.getTILE_SIZE() * 35);
        gp.npc[mapNum][i].setWorldY(gp.getTILE_SIZE() * 17);

        mapNum = 1;
        i = 0;

        //MAP 1
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].setWorldX(gp.getTILE_SIZE() * 19);
        gp.npc[mapNum][i].setWorldY(gp.getTILE_SIZE() * 11);
    }

    public void setEnemy() {
        int mapNum = 0;
        int i = 0;

        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 34, 25); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 37, 26); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 58, 13); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 62, 15); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 60, 19); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 30, 38); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 32, 40); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 33, 36); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 24, 57); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 27, 58); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 28, 56); i++;

        //// DUNGEON 1 ////

        // MAP 1
        mapNum = gp.dungeon1Maps; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12);

        // MAP 3
        mapNum = gp.dungeon1Maps +2; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12);

        // MAP 4
        mapNum = gp.dungeon1Maps +3; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12);

        // MAP 6
        mapNum = gp.dungeon1Maps +5; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12);

        // MAP 9
        mapNum = gp.dungeon1Maps +8; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 18, 10); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 50, 10); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 52, 13);

        // MAP 15
        mapNum = gp.dungeon1Maps +14; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 12);

        //// DUNGEON 2 ////

        // MAP 1
        mapNum = gp.dungeon2Maps; i = 0;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 17, 14); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 20, 15); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 18, 16); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 23, 18); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 25, 13); i++;
        gp.enm[mapNum][i] = new ENM_GreenSlime(gp, 26, 16); i++;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

        // OVERWORLD

        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 36, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 37, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 38, 31); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 56); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 57); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 43, 56); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 43, 57); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,59,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,60,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,61,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,62,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,63,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,64,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,65,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,66,50); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,67,50); i++;

        //// DUNGEON 1 ////

        // MAP 1
        mapNum = gp.dungeon1Maps; i = 0;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 10); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 19, 14);

        // MAP 2
        mapNum = gp.dungeon1Maps +1; i = 0;
        gp.iTile[mapNum][i] = new IT_KeyDoor(gp, 14, 11, "left"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_KeyDoor(gp, 54, 11, "right"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 19, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 20, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 22, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 14); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 13); i++;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 28, 9); i++;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 26, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 27, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 29, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 30, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 31, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 33, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 34, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 26, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 27, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 28, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 29, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 30, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 31, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 32, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 33, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 34, 10); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 24, 14); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 36, 14); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 38, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 39, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 40, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 41, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 42, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 43, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 42, 14); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 43, 14); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,40,12); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,43,12); i++;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 50, 9); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,32,11); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,32,12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 30, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 31, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 33, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 34, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 26, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 33, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 34, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 45, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 44, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 45, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 44, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 41, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 41, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 39, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 39, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 48, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 48, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 48, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 49, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 50, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 51, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 52, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 52, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 52, 9);

        // MAP 4
        mapNum = gp.dungeon1Maps + 3; i = 0;
        gp.iTile[mapNum][i] = new IT_Stairs(gp, 16, 9, true, gp.dungeon1Maps +15, 22, 14); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 14); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 23, 14);

        // MAP 5
        mapNum = gp.dungeon1Maps + 4; i = 0;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 23, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 14); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 23, 14);

        // MAP 6
        mapNum = gp.dungeon1Maps + 5; i = 0;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 19, 15, "down"); i++;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 14, 12, "left"); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 18, 11); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 21, 11); i++;

        // MAP 7
        mapNum = gp.dungeon1Maps + 6; i = 0;
        gp.iTile[mapNum][i] = new IT_Stairs(gp, 23, 9, true, gp.dungeon1Maps +2, 22, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 13); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,19,13); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,20,13); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,19,10); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,20,10); i++;
        gp.iTile[mapNum][i] = new IT_BossDoor(gp, 19, 7); gp.iTile[mapNum][i].index = i;

        // MAP 8
        mapNum = gp.dungeon1Maps + 7; i = 0;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp,18,11, "right"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp,21,11, "left"); gp.iTile[mapNum][i].index = i;

        // MAP 9
        mapNum = gp.dungeon1Maps + 8; i = 0;
        gp.iTile[mapNum][i] = new IT_KeyDoor(gp, 52, 7, "up"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_KeyDoor(gp, 19, 15, "down"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 27, 15); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 28, 15); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 29, 15); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 30, 15); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 31, 15); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 32, 15); i++;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 30, 9); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 28, 19); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 29, 19); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 30, 19); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 27, 20); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 31, 20); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 28, 21); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 29, 21); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 30, 21); i++;
        gp.iTile[mapNum][i] = new IT_CrystalRed(gp, 30, 20); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 34, 20); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 34, 21); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,37,19); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,38,21); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,40,20); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,42,21); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,43,20); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,41,18); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 17); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 18); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 19); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 20); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 38, 22); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 36, 19); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 39, 18); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 40, 18); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 40, 19); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 40, 21); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 40, 22); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 22); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 20); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 19); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 18); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 42, 17); i++;
        gp.iTile[mapNum][i] = new IT_ButtonOff(gp, 43, 22); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp, 39, 11, "up"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp, 38, 12, "left"); gp.iTile[mapNum][i].index = i;

        // MAP 11
        mapNum = gp.dungeon1Maps + 10; i = 0;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 19, 18); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 20, 18); i++;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 19, 11); i++;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 20, 11); i++;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 24, 11, "right"); i++;
        gp.iTile[mapNum][i] = new IT_KeyDoor(gp, 14, 11, "left"); gp.iTile[mapNum][i].index = i;

        // MAP 12
        mapNum = gp.dungeon1Maps + 11; i = 0;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 22, 13); i++;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 17, 13); i++;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 19, 7, "up");

        // MAP 13
        mapNum = gp.dungeon1Maps + 12; i = 0;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 18, 13); i++;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 21, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 19, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 20, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 22, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 23, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 22, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 23, 13);

        // MAP 14
        mapNum = gp.dungeon1Maps + 13; i = 0;
        gp.iTile[mapNum][i] = new IT_Stairs(gp, 22, 14, true, gp.dungeon1Maps +7, 22, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 14); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,19,10); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,20,10); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,19,13); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,20,13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 22, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 23, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 22, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 23, 13);

        // MAP 15
        mapNum = gp.dungeon1Maps + 14; i = 0;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 19, 15, "down"); i++;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 24, 13, "right");

        // MAP 16
        mapNum = gp.dungeon1Maps + 15; i = 0;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 16, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 9); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 11); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 20, 14); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 20, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 21, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 22, 13); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 23, 13); i++;
        gp.iTile[mapNum][i] = new IT_Stairs(gp, 23, 14, false, gp.dungeon1Maps +3, 17, 9);

        // MAP 17
        mapNum = gp.dungeon1Maps + 16; i = 0;
        gp.iTile[mapNum][i] = new IT_Void(gp, 21, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 20, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 19, 11); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 10); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 12); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 16, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 13); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 17, 14); i++;
        gp.iTile[mapNum][i] = new IT_Void(gp, 18, 14); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 16, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOn(gp, 21, 9); i++;
        gp.iTile[mapNum][i] = new IT_LampOff(gp, 23, 11);

        //// DUNGEON 2 ////
        mapNum = gp.dungeon2Maps; i = 0;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 16, 38); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 16, 40); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 17, 40); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 18, 40); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 19, 40); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 20, 40); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 20, 37); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 20, 38); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 20, 39); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 16, 36); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 17, 36); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 18, 36); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 19, 36); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 20, 36); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,21,32); i++;
        gp.iTile[mapNum][i] = new IT_DestructiblePlate(gp,22,32); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 27, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 26, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 26, 30); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 26, 29); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleStone(gp, 27, 29); i++;
        gp.iTile[mapNum][i] = new IT_CrystalBlue(gp, 27, 30); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 16, 26); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 17, 26); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 18, 26); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 19, 26); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeDown(gp, 20, 26); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 16, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 17, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 18, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 19, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 20, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 23, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 24, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 25, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 26, 23); i++;
        gp.iTile[mapNum][i] = new IT_BlockadeUp(gp, 27, 23); i++;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp, 24, 12, "up"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_MovableBlock(gp, 26, 12, "up"); gp.iTile[mapNum][i].index = i; i++;
        gp.iTile[mapNum][i] = new IT_ClosedDoor(gp, 17, 7, "up");
    }
}
