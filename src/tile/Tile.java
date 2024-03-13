package tile;

import java.awt.image.BufferedImage;

public class Tile {

    String name;
    BufferedImage image;
    boolean collision = false;

    public boolean isCollision() { return collision; }
}


