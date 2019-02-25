package Data.Tiled;

import javafx.geometry.Point2D;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;

public class Layer {

    private int[] tileNumbers; // The tile number of the index tile
    private  int tileWidth;
    private int tileHeight;
    private Point2D origin;
    private int rows;
    private int collums;

    public Layer(int[] tileNumbers, Tilemap tilemap, Point2D origin, int rows, int collums) {
        this.tileNumbers = tileNumbers;
        this.tileWidth = tilemap.getTilemapInfo().getTilewidth();
        this.tileHeight = tilemap.getTilemapInfo().getTileheight();
        this.origin = origin;
        this.rows = rows;
        this.collums = collums;
    }

    
}
