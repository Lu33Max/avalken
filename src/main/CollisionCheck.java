package main;

import entity.Entity;

public class CollisionCheck {

    GamePanel gp;

    //// KONSTRUKTOR ////
    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    //// KOLLISION MIT TILES ////
    public void checkTile(Entity entity) {
        // Bestimmt die Weltkoordinaten der solidArea der Entity
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        // Bestimmt die Tilekoordinaten der solidArea der Entity
        int entityLeftCol = entityLeftWorldX / gp.getTILE_SIZE();
        int entityRightCol = entityRightWorldX / gp.getTILE_SIZE();
        int entityTopRow = entityTopWorldY / gp.getTILE_SIZE();
        int entityBottomRow = entityBottomWorldY / gp.getTILE_SIZE();

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            // Bewegt sich die Entity nach oben
            case "up" -> {
                // Bestimme die beiden Tiles, mit denen die Entity Kontakt hat
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTILE_SIZE();
                tileNum1 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityRightCol][entityTopRow];
                // Falls eines der Tiles solide ist, wird collisionOn des Entities auf true gesetzt
                if (gp.tileManager.getTile()[tileNum1].isCollision() || gp.tileManager.getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            // Analog für unten
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTILE_SIZE();
                tileNum1 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.tileManager.getTile()[tileNum1].isCollision() || gp.tileManager.getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            // Analog für links
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTILE_SIZE();
                tileNum1 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityLeftCol][entityTopRow];
                if (gp.tileManager.getTile()[tileNum1].isCollision() || gp.tileManager.getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            //Analog für rechts
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTILE_SIZE();
                tileNum1 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityRightCol][entityBottomRow];
                tileNum2 = gp.mapManager.getMapList().get(gp.currentMap).getMapTileNum()[entityRightCol][entityTopRow];
                if (gp.tileManager.getTile()[tileNum1].isCollision() || gp.tileManager.getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
        }
    }

    //// KOLLISION MIT OBJEKTEN ////
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        // Durchgehen des Objekt-Arrays der aktuellen Karte
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            // Falls ein Objekt an dieser Stelle vorhanden ist
            if (gp.obj[gp.currentMap][i] != null) {

                // Bestimmt die Weltkoordinaten der solidArea der Entity
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                // Bestimmt die Weltkoordinaten der solidArea des Objekts
                gp.obj[gp.currentMap][i].getSolidArea().x = gp.obj[gp.currentMap][i].getWorldX() + gp.obj[gp.currentMap][i].getSolidArea().x;
                gp.obj[gp.currentMap][i].getSolidArea().y = gp.obj[gp.currentMap][i].getWorldY() + gp.obj[gp.currentMap][i].getSolidArea().y;

                // Vorhersage, wo die Entity nach der Bewegung sein wird
                switch (entity.getDirection()) {
                    case "up" -> entity.getSolidArea().y -= entity.getSpeed();
                    case "down" -> entity.getSolidArea().y += entity.getSpeed();
                    case "left" -> entity.getSolidArea().x -= entity.getSpeed();
                    case "right" -> entity.getSolidArea().x += entity.getSpeed();
                }

                // Test, ob sich die beiden solidAreas berühren würden
                if (entity.getSolidArea().intersects(gp.obj[gp.currentMap][i].getSolidArea())) {
                    if (gp.obj[gp.currentMap][i].isCollision()) {
                        // collisionOn der Entity wird auf true gesetzt
                        entity.setCollisionOn(true);
                    }
                    // Falls die Entity ein Spieler ist, wird der index des Objekts zurückgegeben (wichtig zum Aufheben des Objekts)
                    if (player) {
                        index = i;
                    }
                }

                // Die Positionen der solidAreas werden zurückgesetzt
                entity.getSolidArea().x = entity.getSolidAreaDefX();
                entity.getSolidArea().y = entity.getSolidAreaDefY();
                gp.obj[gp.currentMap][i].getSolidArea().x = gp.obj[gp.currentMap][i].getSolidAreaDefX();
                gp.obj[gp.currentMap][i].getSolidArea().y = gp.obj[gp.currentMap][i].getSolidAreaDefY();
            }
        }

        return index;
    }

    //// KOLLISION MIT ENTITY ////
    public int checkEntity(Entity entity, Entity[][] target) {
        // Analog checkObject, nur das hier der Index stets zurückgegeben wird

        int index = 999;

        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {

                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                target[gp.currentMap][i].getSolidArea().x = target[gp.currentMap][i].getWorldX() + target[gp.currentMap][i].getSolidArea().x;
                target[gp.currentMap][i].getSolidArea().y = target[gp.currentMap][i].getWorldY() + target[gp.currentMap][i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case "up" -> entity.getSolidArea().y -= entity.getSpeed();
                    case "down" -> entity.getSolidArea().y += entity.getSpeed();
                    case "left" -> entity.getSolidArea().x -= entity.getSpeed();
                    case "right" -> entity.getSolidArea().x += entity.getSpeed();
                }

                if (entity.getSolidArea().intersects(target[gp.currentMap][i].getSolidArea()) && target[gp.currentMap][i] != entity) {
                    entity.setCollisionOn(true);
                    index = i;
                }

                entity.getSolidArea().x = entity.getSolidAreaDefX();
                entity.getSolidArea().y = entity.getSolidAreaDefY();
                target[gp.currentMap][i].getSolidArea().x = target[gp.currentMap][i].getSolidAreaDefX();
                target[gp.currentMap][i].getSolidArea().y = target[gp.currentMap][i].getSolidAreaDefY();
            }
        }

        return index;
    }

    //// KOLLISION MIT SPIELER ////
    public boolean checkPlayer(Entity entity, Entity player) {
        // Analog checkObject, aber statt dem index wird hier eine boolean zurückgegeben, ob ein Spieler berührt wurde

        boolean hitPlayer = false;

        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        player.getSolidArea().x = player.getWorldX() + player.getSolidArea().x;
        player.getSolidArea().y = player.getWorldY() + player.getSolidArea().y;

        switch (entity.getDirection()) {
            case "up" -> entity.getSolidArea().y -= entity.getSpeed();
            case "down" -> entity.getSolidArea().y += entity.getSpeed();
            case "left" -> entity.getSolidArea().x -= entity.getSpeed();
            case "right" -> entity.getSolidArea().x += entity.getSpeed();
        }

        if (entity.getSolidArea().intersects(player.getSolidArea())) {
            entity.setCollisionOn(true);
            hitPlayer = true;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefX();
        entity.getSolidArea().y = entity.getSolidAreaDefY();
        player.getSolidArea().x = player.getSolidAreaDefX();
        player.getSolidArea().y = player.getSolidAreaDefY();

        return hitPlayer;
    }
}
