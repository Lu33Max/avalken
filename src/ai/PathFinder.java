package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, endNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }

    public void instantiateNode() {
        // Die Welt wird in Nodes aufgeteilt, welche im Node[] Array gespeichert werden
        node = new Node[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int col = 0, row = 0;
        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            node[col][row] = new Node(col, row);
            col++;

            if(col == gp.getMaxWorldCol()) {
                col = 0;
                row ++;
            }
        }
    }
    public void resetNode() {
        // Setzt alle Variablen wieder auf den Ursprungszustand zurück
        int col = 0, row = 0;

        while (col < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxCol() && row < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxRow()) {
            // RESET OPEN, CHECKED AND SOLID STATE
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;

            if(col == gp.getMaxWorldCol()) {
                col = 0;
                row ++;
            }
        }

        //RESET OTHER SETTINGS
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNode(int startCol, int startRow, int endCol, int endRow) {
        // Zurücksetzen der Variablen
        resetNode();

        // Position des Start- und Endnode werden gesetzt
        startNode = node[startCol][startRow];
        currentNode = startNode;
        endNode = node[endCol][endRow];
        openList.add(currentNode);

        // Scannen der Karte nach nicht begehbaren Nodes und Setzen von F-, G- und H-Cost
        int col = 0, row = 0;
        while(col < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxCol() && row < gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMaxRow()) {

            // Scannen nach soliden Standard Tiles
            int tileNum = gp.getMapManager().getMapList().get(gp.getCurrentMap()).getMapTileNum()[col][row];
            if(gp.getTileManager().getTile()[tileNum].isCollision()) {
                node[col][row].solid = true;
            }

            // Scannen nach soliden Interactive Tiles
            for(int i = 0; i < gp.getiTile()[0].length; i++) {
                if(gp.getiTile()[gp.getCurrentMap()][i] != null && gp.getiTile()[gp.getCurrentMap()][i].destructible) {
                    int itCol = gp.getiTile()[gp.getCurrentMap()][i].getWorldX() / gp.getTILE_SIZE();
                    int itRow = gp.getiTile()[gp.getCurrentMap()][i].getWorldY() / gp.getTILE_SIZE();
                    node[itCol][itRow].solid = true;
                }
            }

            // Setzen der Costs
            getCost(node[col][row]);

            // Bewgung zur nächsten Node
            col++;
            // Ist das Ende der Welt in einer Reihe erreicht, wird die Spalte wieder auf 0 gesetzt und die Suche in der nächsten Reihe wiederholt
            if(col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node) {
        // Setzen der G-Cost (Abstand des Node vom Startpunkt)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // Setzen der H-Cost (Abstand des Node vom Endpunkt)
        xDistance = Math.abs(node.col - endNode.col);
        yDistance = Math.abs(node.row - endNode.row);
        node.hCost = xDistance + yDistance;

        // Setzen der F-Cost (Summe aus -G und H-Cost)
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search() {
        //// Die eigentliche Pfadsuche nach dem A* Algorithmus ////

        // Wiederholung, solange das Ziel oder die maximale Anzahl Schritte nicht erreicht wurde (Die Limitierung der Schritte dient der Begrenzung der maximalen Suchdauer)
        while (!goalReached && step < 500) { // Limits maximum search time
            int col = currentNode.col;
            int row = currentNode.row;

            // Markiert den aktuelle Node als überprüft und entfernt ihn aus der Liste der noch offenen Nodes
            currentNode.checked = true;
            openList.remove(currentNode);

            // Öffnen der vier anliegenden Nodes
            if(row - 1 >= 0) openNode(node[col][row-1]);
            if(row + 1 <= gp.getMaxWorldRow()) openNode(node[col][row+1]);
            if(col - 1 >= 0) openNode(node[col-1][row]);
            if(col + 1 <= gp.getMaxWorldCol()) openNode(node[col+1][row]);

            //// Suchen der besten Node aus den neu geöffneten ////
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            // Durchgehen aller Nodes in der OpenList
            for(int i = 0; i < openList.size(); i++) {
                // Überprüft, ob die F-Cost der aktuellen Node niedriger ist, als die bisher niedrigste
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // Falls die F-Cost gleich ist, wird die mit der geringeren G-Cost genommen
                else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // Konnte kein Node mehr geöffnet werden, wird die Suche abgebrochen
            if(openList.size() == 0) {
                break;
            }

            // Der Node mit den geringsten Kosten wird zur neuen currentNode
            currentNode = openList.get(bestNodeIndex);

            // Entspricht der aktuelle Node dem EndNode, so wird die Suche abgebrochen und der Pfad zurückverfolgt
            if(currentNode == endNode) {
                goalReached = true;
                trackPath();
            }

            // Anzahl der gegangenen Schritte wird erhöht
            step++;
        }
        // Ausgabe, ob das Ziel erreicht wurde
        return goalReached;
    }
    public void openNode (Node node) {
        // Prüfen, ob der Node weder geöffnet, bereits überprüft noch solide ist
        if(!node.open && !node.checked && !node.solid) {
            // Node wird der openList hinzugefügt und erhält die aktuelle Node als parent (nötig zum Zurückverfolgen des Pfads)
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void trackPath() {
        // EndNode wird als aktuelle Node gesetzt
        Node current = endNode;

        // Solange der aktuelle Node nicht dem StartNode entspricht, fügt die aktuelle Node ihr parent der pathList hinzu, um so den gegangenen Weg aufzuzeichnen
        while(current != startNode) {
            pathList.add(0,current);
            current =  current.parent;
        }
    }

    //// GETTER ////
    public ArrayList<Node> getPathList() { return pathList; }
}
