package Data.Tiled;

import Data.Tiled.TilemapInfo;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class Tileset {
    private int firstGuid;
    private int lastGuid;
    private BufferedImage[] tiles;
    public Tileset(TilemapInfo.TileSet tileSet, TilemapInfo tilemapInfo) throws Exception {
        this.firstGuid = tileSet.getFirstgid();
        JsonObject tileSetJson = Json.createReader(
            new FileInputStream(
                new File(
                    tilemapInfo.getTilemapFile().getParent(), tileSet.getSource()
                )
            )
        ).readObject();
        this.lastGuid = this.firstGuid + tileSetJson.getInt("tilecount") - 1;
        String imagePath = new File(tilemapInfo.getTilemapFile().getParent(), tileSetJson.getString("image")).getPath();
        int verticalTiles = tileSetJson.getInt("columns");
        int horizontalTiles = tileSetJson.getInt("tilecount") / verticalTiles;
        this.tiles = this.cutImage(imagePath, verticalTiles, horizontalTiles);
    }

    private BufferedImage[] cutImage(String filepath, int tilesHorizontal, int tilesVectical) throws Exception {
        BufferedImage image = ImageIO.read(new File(filepath));
        int tileWidth = image.getWidth() / tilesHorizontal;
        int tileHeight = image.getHeight() / tilesVectical;
        int tileCount = tilesHorizontal * tilesVectical;

        BufferedImage[] cuttedTiles = new BufferedImage[tileCount];

        for (int i = 0; i < tileCount; i++) {
            cuttedTiles[i] = image.getSubimage(tileWidth * (i % tilesHorizontal), tileHeight * (i / tilesVectical), tileWidth, tileHeight);
        }

        return cuttedTiles;
    }

    public int getFirstGuid() {
        return firstGuid;
    }

    public int getLastGuid() {
        return lastGuid;
    }

    public BufferedImage[] getTiles() {
        return tiles;
    }
}