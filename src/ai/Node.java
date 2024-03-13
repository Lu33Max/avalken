package ai;

public class Node {

    Node parent;
    int col, row;
    int gCost, hCost, fCost;
    boolean solid, open, checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }

    //// GETTER ////

    public int getCol() { return col; }
    public int getRow() { return row; }
}
