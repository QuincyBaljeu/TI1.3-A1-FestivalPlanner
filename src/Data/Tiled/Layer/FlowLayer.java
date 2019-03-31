package Data.Tiled.Layer;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FlowLayer {

    private int[][] data2D;
    private int[][] distMap;
    private Point2D[][] flowMap;

    private TiledObject endPoint;

    public FlowLayer(TileLayer collisionLayer, TiledObject goal, int[][] data2D) {
        this.data2D = data2D;
        this.endPoint = goal;
        generateDistMap(collisionLayer);
        generateFlowMap(collisionLayer);

    }

    private void generateDistMap(TileLayer collisionLayer) {
        this.distMap = new int[collisionLayer.getWidth()][collisionLayer.getHeight()];
        resetDistanceMap();

        int centerX = (int) this.endPoint.getX() / 32;
        int centerY = (int) this.endPoint.getY() / 32;

        Queue<Point2D> frontier = new LinkedList<>();
        frontier.offer(new Point2D(centerX, centerY));
        this.distMap[centerX][centerY] = 0;

        while (!frontier.isEmpty()) {
            Point2D current = frontier.peek();
            frontier.poll();

            for (Point2D neighbor : getNeighbors(current)) {
                frontier.add(neighbor);
                this.distMap[(int) neighbor.getX()][(int) neighbor.getY()] = this.distMap[(int) current.getX()][(int) current.getY()] + 1;
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
    }

    private void generateFlowMap(TileLayer collisionLayer) {
        this.flowMap = new Point2D[collisionLayer.getWidth()][collisionLayer.getHeight()];

        for (int x = 0; x < this.data2D.length; x++) {
            for (int y = 0; y < this.data2D[0].length; y++) {
                this.flowMap[x][y] = new Point2D(0, 0);
            }
        }

        for (int x = 0; x < this.distMap.length; x++) {
            for (int y = 0; y < this.distMap[0].length; y++) {
                if (this.data2D[x][y] != -1) {
                    ArrayList<Integer> leftRight = getLeftRight(new Point2D(x, y));
                    ArrayList<Integer> topBot = getTopBot(new Point2D(x, y));
                    double flowX = leftRight.get(0) - leftRight.get(1);
                    double flowY = topBot.get(0) - topBot.get(1);
                    if (this.distMap[x][y] != 0)
                        this.flowMap[x][y] = new Point2D(flowX, flowY);
                }
            }
        }
    }


    private void resetDistanceMap() {
        for (int x = 0; x < this.data2D.length; x++) {
            for (int y = 0; y < this.data2D[0].length; y++) {
                this.distMap[x][y] = -1;
            }
        }
    }

    private ArrayList<Point2D> getNeighbors(Point2D center) {
        ArrayList<Point2D> neighbors = new ArrayList<>();

        int centerX = (int) center.getX();
        int centerY = (int) center.getY();

        for (int x = centerX - 1; x <= centerX + 1; x++) {
            if (x >= 0 && x < data2D.length) {
                for (int y = centerY - 1; y <= centerY + 1; y++)
                    if (y >= 0 && y < data2D[0].length && data2D[x][y] == 0 && !(centerX == x && centerY == y)) {
                        if (this.distMap[x][y] == -1) {
                            neighbors.add(new Point2D(x, y));
                        }

                    }
            }
        }
        return neighbors;
    }

    private ArrayList<Integer> getTopBot(Point2D center) {
        int x = (int)center.getX();
        ArrayList<Integer> topBot = new ArrayList<>();
        for (int y = (int) center.getY() - 1; y <= center.getY() + 1; y += 2) {
            if (y >= 0 && y < this.distMap[0].length && this.data2D[x][y] == 0)
                topBot.add(this.distMap[x][y]);
            else if (topBot.size() == 1) {
                topBot.add(topBot.get(0) + 1);
            } else {
                if (this.distMap[x].length != y + 2)
                    topBot.add(this.distMap[x][y + 2] + 1);

                else {
                    topBot.add(this.distMap[x][y + 1] + 1);
                }
            }
        }
        return topBot;
    }

    private ArrayList<Integer> getLeftRight(Point2D center) {
        int y = (int) center.getY();
        ArrayList<Integer> topBot = new ArrayList<>();
        for (int x = (int)center.getX() - 1; x <= center.getX() + 1; x += 2) {
            if (x >= 0 && x < this.distMap.length && data2D[x][y] == 0)
                topBot.add(this.distMap[x][y]);
            else if (topBot.size() == 1) {
                topBot.add(topBot.get(0) + 1);
            } else {
                if (this.distMap.length != x + 2)
                    topBot.add(this.distMap[x + 2][y] + 1);

                else
                    topBot.add(this.distMap[x + 1][y] + 1);

            }
        }
        return topBot;
    }

    public int[][] getDistMap() {
        return distMap;
    }

    public Point2D[][] getFlowMap() {
        return flowMap;
    }
}
