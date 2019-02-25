package Data.Tiled;

import javafx.geometry.Point2D;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static javax.json.Json.createReader;

public class Tilemap {
    private TilemapInfo tilemapInfo;
    private BufferedImage[] tiles;

    public Tilemap(TilemapInfo tilemapInfo) throws Exception {
        this.tilemapInfo = tilemapInfo;
        ArrayList<Tileset> tileSets = new ArrayList<>();

        for (TilemapInfo.TileSet tileSet : tilemapInfo.getTilesets()){
            tileSets.add(new Tileset(tileSet, tilemapInfo));
        }

        int tileCount = 0;
        for (Tileset tileset : tileSets){
            if (tileCount < tileset.getLastGuid()){
                tileCount = tileset.getLastGuid();
            }
        }
        this.tiles = new BufferedImage[tileCount];
        for (Tileset tileset : tileSets){
            int firstGid = tileset.getFirstGuid();
            int lastGid = tileset.getLastGuid();
            BufferedImage[] tilesetTiles = tileset.getTiles();
            for (int i = firstGid; i < lastGid; i++){
                this.tiles[firstGid + i-1] = tilesetTiles[i-firstGid];
            }
        }
    }

    public int getTileCount(){
        return this.tiles.length;
    }

    public BufferedImage getTile(int gid){
        return this.tiles[gid];
    }

    public TilemapInfo getTilemapInfo() {
        return tilemapInfo;
    }

    public Layer[] getLayers(){
        Layer[] layers = new Layer[this.getTilemapInfo().getLayers().length];
        TilemapInfo.Layer[] layers1 = this.getTilemapInfo().getLayers();
        for (int i = 0; i < layers1.length; i++) {
            TilemapInfo.Layer layerInfo = layers1[i];
            Layer layer = new Layer(
                layerInfo.getData(),
                this,
                new Point2D(layerInfo.getX(), layerInfo.getY()),
                layerInfo.getHeight(),
                layerInfo.getWidth()
            );
            layers[i] = layer;
        }
        return layers;
    }
}