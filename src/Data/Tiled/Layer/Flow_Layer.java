package Data.Tiled.Layer;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Flow_Layer {

    private int[][] data2D;
    private int[][] distMap;
    private Point2D[][] flowMap;

    private TiledObject endPoint;

    public Flow_Layer(TileLayer collisionLayer, TiledObject goal, int[][] data2D) {
        this.data2D = data2D;

        this.endPoint = goal;

        generateDistMap(collisionLayer);

    }

    private void generateDistMap(TileLayer collisionLayer) {
        this.distMap = new int[collisionLayer.getWidth()][collisionLayer.getHeight()];

        Queue<Integer[][]> frontier = new LinkedList<>();
        frontier.offer(new Integer[endPoint.getX()][endPoint.getY()]);
        startTile.setDistCost(0);

        while (!frontier.isEmpty()) {
            int current = frontier.peek();
            frontier.poll();

            for (Tile neighbor : getNeighbors(current)) {
                frontier.add(neighbor);
                neighbor.setDistCost(current.getDistCost() + 1);

            }
        }
        for (int x = 0; x < this.tiles.length; x++) {
            for (int y = 0; y < this.tiles[0].length; y++) {
                if (this.tiles[x][y].isAcesAble()) {
                    ArrayList<Integer> leftRight = getLeftRight(tiles[x][y]);
                    ArrayList<Integer> topBot = getTopBot(tiles[x][y]);
                    double flowX = leftRight.get(0) - leftRight.get(1);
                    double flowY = topBot.get(0) - topBot.get(1);
                    if (this.tiles[x][y].getDistCost() != 0)
                        this.tiles[x][y].setFlow(new java.awt.geom.Point2D.Double(flowX, flowY));
                }
            }
        }
    }

    private ArrayList<Integer[][]> getNeighbors(Integer[][] center) {
        ArrayList<Integer[][]> neighbors = new ArrayList<>();
        for (int x = center[0].length - 1; x <= center[0].length + 1; x++) {
            if (x >= 0 && x < this.getSize().getX()) {
                for (int y = tile.getY() - 1; y <= tile.getY() + 1; y++)
                    if (y >= 0 && y < this.getSize().getY() && tiles[x][y].isAcesAble() && tiles[x][y] != tile && tiles[x][y].getDistCost() == -1) {
                        neighbors.add(tiles[x][y]);
                    }
            }
        }
        return neighbors;
    }

}
