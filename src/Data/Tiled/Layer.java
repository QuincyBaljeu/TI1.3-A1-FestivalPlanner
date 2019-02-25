package Data.Tiled;

import javafx.geometry.Point2D;

public class Layer {

    private Tilemap tileMap;
    private int[] tileNumbers; // The tile number of the index tile
    private  int width;
    private int height;
    private Point2D origin;

    public Layer(Tilemap tileMap, int[] tileNumbers, int width, int height, Point2D origin) {
        this.tileMap = tileMap;
        this.tileNumbers = tileNumbers;
        this.width = width;
        this.height = height;
        this.origin = origin;
    }


}
