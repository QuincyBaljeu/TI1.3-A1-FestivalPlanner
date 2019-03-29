package Data.Tiled.Layer;

import javafx.geometry.Point2D;

public class Flow_Layer {

    private int[][] distMap;
    private Point2D[][] flowMap;

    public Flow_Layer(TileLayer collisionLayer) {
        System.out.println(collisionLayer);
    }

}
