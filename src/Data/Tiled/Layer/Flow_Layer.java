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
        resetDistanceMap();

        int centerX = (int)this.endPoint.getX()/32;
        int centerY = (int)this.endPoint.getY()/32;

        Queue<Point2D> frontier = new LinkedList<>();
        frontier.offer(new Point2D(centerX, centerY));
        this.distMap[centerX][centerY] = 0;

        while (!frontier.isEmpty()) {
            Point2D current = frontier.peek();
            frontier.poll();

            for (Point2D neighbor : getNeighbors(current)) {
                frontier.add(neighbor);
                this.distMap[(int)neighbor.getX()][(int)neighbor.getY()] = this.distMap[(int)current.getX()][(int)current.getY()] + 1;
            }
        }
/*-------------------printing map
//        for (int[] ints : this.distMap) {
//            for (int anInt : ints) {
//                System.out.print(anInt);
//            }
//            System.out.println("");
//        }
*/

//        for (int x = 0; x < this.tiles.length; x++) {
//            for (int y = 0; y < this.tiles[0].length; y++) {
//                if (this.tiles[x][y].isAcesAble()) {
//                    ArrayList<Integer> leftRight = getLeftRight(tiles[x][y]);
//                    ArrayList<Integer> topBot = getTopBot(tiles[x][y]);
//                    double flowX = leftRight.get(0) - leftRight.get(1);
//                    double flowY = topBot.get(0) - topBot.get(1);
//                    if (this.tiles[x][y].getDistCost() != 0)
//                        this.tiles[x][y].setFlow(new java.awt.geom.Point2D.Double(flowX, flowY));
//                }
//            }
//        }
    }

    private void resetDistanceMap() {
        for (int x = 0; x < this.data2D.length; x++) {
            for (int y = 0; y < this.data2D[0].length; y++) {
                this.distMap[x][y] = -1;
            }
        }
    }

    private  ArrayList<Point2D> getNeighbors(Point2D center) {
        ArrayList<Point2D> neighbors = new ArrayList<>();

        int centerX = (int)center.getX();
        int centerY = (int)center.getY();

        for (int x = centerX - 1; x <= centerX + 1; x++) {
            if (x >= 0 && x < data2D.length) {
                for (int y = centerY - 1; y <= centerY + 1; y++)
                    if (y >= 0 && y < data2D[0].length && data2D[x][y] == 0 && !(centerX == x && centerY == y)) {
                        if (this.distMap[x][y] == -1) {
                            System.out.println(x + "<-x y->" + y + "  |  " + centerX + "<-x y->" + centerY);
                            neighbors.add(new Point2D(x, y));
                        }

                    }
            }
        }

        return neighbors;
    }

}
