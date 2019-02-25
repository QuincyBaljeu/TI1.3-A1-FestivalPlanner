package Data.Tiled;

import javafx.geometry.Point2D;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;

/**
 * @author Jasper en Sjors
 */

public class Layer {

    private int[] tileNumbers; // The tile number of the index tile
    private Tilemap tilemap;
    private  int tileWidth;
    private int tileHeight;
    private Point2D origin;
    private int rows;
    private int collums;

    public Layer(int[] tileNumbers, Tilemap tilemap, Point2D origin, int rows, int collums) {
        this.tileNumbers = tileNumbers;
        this.tilemap = tilemap;
        this.tileWidth = tilemap.getTilemapInfo().getTilewidth();
        this.tileHeight = tilemap.getTilemapInfo().getTileheight();
        this.origin = origin;
        this.rows = rows;
        this.collums = collums;
    }

    public void draw(FXGraphics2D g){
        int imageIndex = 0;

        for(int y = 0; y < this.rows; y++){

            for(int x = 0; x < this.collums; x++){
                AffineTransform at = new AffineTransform();
                at.translate(x * tileWidth, y * tileHeight);
                g.drawImage(
                        tilemap.getTile(
                                this.tileNumbers[imageIndex]),
                        at,
                        null
                );
                imageIndex++;
            }
        }
    }
}
