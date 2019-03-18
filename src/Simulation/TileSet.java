package Simulation;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class TileSet {

    private int height;
    private int width;
    private int firstGrid;
    private int amountSubPic;

    private BufferedImage[] tiles;
    private BufferedImage sprite;


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TileSet(JsonObject jsonTileSet, int height, int width) {
        this.height = height;
        this.width = width;
        this.firstGrid = jsonTileSet.getInt("firstgid") -1;
        try {
            InputStream tileSetFile = new FileInputStream(Main.path + "\\rec\\Tiled\\" + jsonTileSet.getString("source"));
            JsonReader tileSetReader = Json.createReader(tileSetFile);
            String link = Main.path + tileSetReader.readObject().getString("image");
            this.sprite = ImageIO.read(new File(link));
            System.out.println(this.sprite.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.amountSubPic = (sprite.getHeight() * sprite.getWidth()) / (width * height);
        System.out.println(amountSubPic);
        this.tiles = new BufferedImage[amountSubPic + firstGrid];

        for(int i = 0; i < amountSubPic; i++) {
            this.tiles[i + firstGrid] = this.sprite.getSubimage((width * (i% (sprite.getWidth()/width))), (height * (i/ (sprite.getWidth()/width))), width, height);
        }
    }

    public void draw(Graphics2D graphics) {
        for(int i = firstGrid; i < this.amountSubPic + firstGrid; i++) {
            graphics.drawImage(this.tiles[i], (width * (i% (sprite.getWidth()/width))), (height * (i/ (sprite.getWidth()/width))), null);
        }
    }

    public BufferedImage[] getTiles() {
        return tiles;
    }

    public BufferedImage getTile(int dataNumb) {
        if (dataNumb < this.firstGrid)
            return null;
        if (dataNumb > this.amountSubPic + this.firstGrid)
            return null;
        return this.tiles[dataNumb];
    }
}
