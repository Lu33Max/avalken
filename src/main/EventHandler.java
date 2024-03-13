package main;

import entity.Entity;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    boolean canTouchEvent = true;
    int tempMap;
    double tempCol, tempRow;

    //// KONSTRUKTOR ////
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][100][100];

        int map = 0;
        int col = 0;
        int row = 0;

        // Das eventRect-Array wird vorläufig mit eventRects gefüllt
        while(map < gp.maxMap && col < 100 && row < 100) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 0;
            eventRect[map][col][row].y = 0;
            eventRect[map][col][row].width = gp.getTILE_SIZE();
            eventRect[map][col][row].height = gp.getTILE_SIZE();
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == 99) {
                col = 0;
                row++;

                if(row == 99) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    //// TEST AUF EVENT ////
    public void checkEvent(Entity player) {
        // Überprüft, ob ein Spieler an einer bestimmten Stelle der Karte mit einer bestimmten Richtung steht und führt anschließend eine Aktion aus

        //// OVERWORLD ////
        // MAP 1
        if (hit(0, 19, 29, "any")) teleport(1, 19, 16);
        if (hit(0, 71, 36, "up") || hit(0, 72, 36, "up")) teleport(gp.dungeon1Maps+10, 19.5, 23);
        // MAP 2
        else if (hit(1, 19, 17, "any")) teleport(0, 19, 30);
        else if (hit(1, 19, 13, "up")) speak(gp.npc[1][0], player);

        //// DUNGEON 2 ////
        // MAP 1
        else if(hit(gp.dungeon1Maps, 24, 10, "right") || hit(gp.dungeon1Maps, 24, 11, "right")) teleport(gp.dungeon1Maps +1, 15, 11.5);
        // MAP 2
        else if(hit(gp.dungeon1Maps +1, 14, 11, "left") || hit(gp.dungeon1Maps +1, 14, 12, "left")) teleport(gp.dungeon1Maps, 23, 10.5);
        else if(hit(gp.dungeon1Maps +1, 19, 16, "down") || hit(gp.dungeon1Maps +1, 20, 16, "down")) teleport(gp.dungeon1Maps +16, 19.5, 8);
        else if(hit(gp.dungeon1Maps +1, 55, 11, "right") || hit(gp.dungeon1Maps +1, 55, 12, "right")) teleport(gp.dungeon1Maps +2, 15, 11.5);
        else if(hit(gp.dungeon1Maps +1, 52, 16, "down") || hit(gp.dungeon1Maps +1, 53, 16, "down")) teleport(gp.dungeon1Maps +3, 22.5, 8);
        // MAP 3
        else if(hit(gp.dungeon1Maps +2, 14, 11, "left") || hit(gp.dungeon1Maps +2, 14, 12, "left")) teleport(gp.dungeon1Maps +1, 54, 11.5);
        // MAP 4
        else if(hit(gp.dungeon1Maps +3, 22, 7, "up") || hit(gp.dungeon1Maps +3, 23, 7, "up")) teleport(gp.dungeon1Maps +1, 52.5, 15);
        // MAP 5
        else if(hit(gp.dungeon1Maps +4, 25, 12, "right") || hit(gp.dungeon1Maps +4, 25, 13, "right")) teleport(gp.dungeon1Maps +5, 15, 12.5);
        // MAP 6
        else if(hit(gp.dungeon1Maps +5, 14, 12, "left") || hit(gp.dungeon1Maps +5, 14, 13, "left")) teleport(gp.dungeon1Maps +4, 24, 12.5);
        else if(hit(gp.dungeon1Maps +5, 19, 16, "down") || hit(gp.dungeon1Maps +5, 20, 16, "down")) teleport(gp.dungeon1Maps +6, 19.5, 8);
        // MAP 7
        else if(hit(gp.dungeon1Maps +6, 19, 7, "up") || hit(gp.dungeon1Maps +6, 20, 7, "up")) teleport(gp.dungeon1Maps +5, 19.5, 14);
        // MAP 8
        else if(hit(gp.dungeon1Maps +7, 22, 16, "down") || hit(gp.dungeon1Maps +7, 23, 16, "down")) teleport(gp.dungeon1Maps +8, 52.5, 8);
        // MAP 9
        else if(hit(gp.dungeon1Maps +8, 52, 7, "up") || hit(gp.dungeon1Maps +8, 53, 7, "up")) teleport(gp.dungeon1Maps +7, 22.5, 15);
        else if(hit(gp.dungeon1Maps +8, 19, 16, "down") || hit(gp.dungeon1Maps +8, 20, 16, "down")) teleport(gp.dungeon1Maps +9, 19.5, 8);
        else if(hit(gp.dungeon1Maps +8, 14, 11, "left") || hit(gp.dungeon1Maps +8, 14, 12, "left")) teleport(gp.dungeon1Maps +10, 24, 11.5);
        // MAP 10
        else if(hit(gp.dungeon1Maps +9, 19, 7, "up") || hit(gp.dungeon1Maps +9, 20, 7, "up")) teleport(gp.dungeon1Maps +8, 19.5, 15);
        // MAP 11
        else if (hit(gp.dungeon1Maps+10, 19, 24, "down") || hit(gp.dungeon1Maps+10, 20, 24, "down")) teleport(0, 71.5, 37);
        else if(hit(gp.dungeon1Maps +10, 25, 11, "right") || hit(gp.dungeon1Maps +10, 25, 12, "right")) teleport(gp.dungeon1Maps +8, 15, 11.5);
        else if(hit(gp.dungeon1Maps +10, 14, 11, "left") || hit(gp.dungeon1Maps +10, 14, 12, "left")) teleport(gp.dungeon1Maps +11, 24, 11.5);
        // MAP 12
        else if(hit(gp.dungeon1Maps +11, 25, 12, "right") || hit(gp.dungeon1Maps +11, 25, 13, "right")) teleport(gp.dungeon1Maps +10, 15, 11.5);
        else if(hit(gp.dungeon1Maps +11, 19, 7, "up") || hit(gp.dungeon1Maps +11, 20, 7, "up")) teleport(gp.dungeon1Maps +12, 19.5, 15);
        // MAP 13
        else if(hit(gp.dungeon1Maps +12, 19, 16, "down") || hit(gp.dungeon1Maps +12, 20, 16, "down")) teleport(gp.dungeon1Maps +11, 19.5, 8);
        // MAP 14
        else if(hit(gp.dungeon1Maps +13, 19, 7, "up") || hit(gp.dungeon1Maps +13, 20, 7, "up")) teleport(gp.dungeon1Maps +14, 19.5, 14);
        // MAP 15
        else if(hit(gp.dungeon1Maps +14, 19, 16, "down") || hit(gp.dungeon1Maps +14, 20, 16, "down")) teleport(gp.dungeon1Maps +13, 19.5, 8);
        else if(hit(gp.dungeon1Maps +14, 25, 13, "right") || hit(gp.dungeon1Maps +14, 25, 14, "right")) teleport(gp.dungeon1Maps +15, 15, 13.5);
        // MAP 16
        else if(hit(gp.dungeon1Maps +15, 14, 13, "left") || hit(gp.dungeon1Maps +15, 14, 14, "left")) teleport(gp.dungeon1Maps +14, 24, 13.5);
        else if(hit(gp.dungeon1Maps +15, 22, 7, "up") || hit(gp.dungeon1Maps +15, 23, 7, "up")) teleport(gp.dungeon1Maps +16, 22.5, 15);
        // MAP 17
        else if(hit(gp.dungeon1Maps +16, 19, 7, "up") || hit(gp.dungeon1Maps +16, 20, 7, "up")) teleport(gp.dungeon1Maps +1, 19.5, 15);
        else if(hit(gp.dungeon1Maps +16, 22, 16, "down") || hit(gp.dungeon1Maps +16, 23, 16, "down")) teleport(gp.dungeon1Maps +15, 22.5, 8);
    }
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        // Test, ob sich der Spieler auf der richtigen Karte befindet
        if(map == gp.currentMap) {
            // Die tatsächliche Weltposition des EventRect wird gesetzt
            eventRect[map][col][row].x = col * gp.getTILE_SIZE() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.getTILE_SIZE() + eventRect[map][col][row].y;

            //// Spieler 1 ////
            // Die Weltkoordinaten der solidArea von Spieler 1 wird gesetzt
            gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getWorldX() + gp.getPlayer1().getSolidArea().x;
            gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getWorldY() + gp.getPlayer1().getSolidArea().y;

            // Test, ob sich das eventRect und die solidArea überschneiden und der Spieler die korrekte Richtung hat
            if (gp.getPlayer1().getSolidArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.getPlayer1().getDirection().contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                }
            }

            // Position der solidArea wird zurückgesetzt
            gp.getPlayer1().getSolidArea().x = gp.getPlayer1().getSolidAreaDefX();
            gp.getPlayer1().getSolidArea().y = gp.getPlayer1().getSolidAreaDefY();

            //// Spieler 2 ////
            // Analog
            if(gp.getPlayerCount() == 2) {
                gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getWorldX() + gp.getPlayer2().getSolidArea().x;
                gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getWorldY() + gp.getPlayer2().getSolidArea().y;

                if (gp.getPlayer2().getSolidArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                    if (gp.getPlayer2().getDirection().contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                        hit = true;
                    }
                }

                gp.getPlayer2().getSolidArea().x = gp.getPlayer2().getSolidAreaDefX();
                gp.getPlayer2().getSolidArea().y = gp.getPlayer2().getSolidAreaDefY();
            }

            // Position des EventRect wird zurückgesetzt
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    //// AKTIONEN ////
    public void teleport(int map, double col, double row) {

        // Startet eine Übergangsanimation und teleportiert den Spieler
        gp.gameState = gp.TRANSITION_STATE;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSoundEffect(13);
    }
    public void speak(Entity entity, Entity player) {
        // Nur für den Händler, um ihn über den tresen hinweg ansprechen zu können
        if(gp.keyHandler.enterPressed) {
            gp.gameState = gp.DIALOGUE_STATE;
            gp.keyHandler.enterPressed = false;
            entity.speak(player);
        }
    }
}
