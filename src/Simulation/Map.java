package Simulation;

import javax.json.*;
import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

    private ArrayList<TileSet> tileSets;
    private ArrayList<Layer> layers;
    private ObjectLayer objectLayer;

    private BufferedImage cacheImage;

    private int height;
    private int width;


    public Map(JsonObject jsonMap) {

        //johan optimalize
                this.cacheImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D imageGraphics = cacheImage.createGraphics();
        //
        this.height = jsonMap.getInt("height");
        this.width = jsonMap.getInt("width");

        this.layers = new ArrayList<>();
        JsonArray layers = jsonMap.getJsonArray("layers");
        for (int i = 0; i < layers.size() - 1; i++) {
            this.layers.add(new Layer(layers.getJsonObject(i)));
        }
        this.objectLayer = new ObjectLayer(layers.getJsonObject(layers.size() - 1));


        this.tileSets = new ArrayList<>();
        JsonArray tileSets = jsonMap.getJsonArray("tilesets");
        for (int i = 0; i < tileSets.size(); i++) {
            int height = jsonMap.getInt("tileheight");
            int width = jsonMap.getInt("tilewidth");
            this.tileSets.add(new TileSet(tileSets.getJsonObject(i), height, width));
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.cacheImage, 0, 0, null);
//        drawCache(graphics);
    }

    public void drawCache() {
        Graphics2D graphics2D = this.cacheImage.createGraphics();
        for (Layer layer : this.layers) {
            if (layer.getVisible()){
                drawLayer(layer, graphics2D);
            }
        }
    }

    public boolean hasCollision(Visitor visitor) {
        Point2D currentLocation = visitor.getPosition();
        Layer boundryLayer =  layers.get(layers.size()-1);
        int tileWidth = tileSets.get(0).getWidth();
        int tileHeight = tileSets.get(0).getHeight();
        //System.out.println(boundryLayer.getData().size());
        for (int x = 0; x < width-1; x++) {
            for (int y = 0; y < height-1; y++) {
                int currColision = boundryLayer.getData().getJsonNumber(x*width+y).intValue();
                //if (currColision != 0)
                //    System.out.println("col: " + currColision);
                if (currColision == 441) {
                    //System.out.println("Possible col");
                    if (
                        (visitor.getPosition().getX()>= x && visitor.getPosition().getX() <= x + tileWidth)
                        &&
                        (visitor.getPosition().getY()>= y && visitor.getPosition().getY() <= y + tileHeight)
                    ){
                        return true;
                    }
                    /*
                    Area area = new Area(new Rectangle2D.Double(x* tileSets.get(0).getWidth(), y*tileSets.get(0).getHeight(), tileSets.get(0).getWidth(), tileSets.get(0).getHeight()));
                    if (area.contains(currentLocation)) {
                        //System.out.println("Simulation.Visitor " + visitor.getPosition() + " has collision with: " + area);
                        return true;
                    }
                    */
                }
            }
        }
        return false;
    }


    private void drawLayer(Layer layer, Graphics2D graphics) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, layer.getOpacity()));
        JsonArray data = layer.getData();

        for (int i = 0; i < data.size(); i++) {
            BufferedImage tile = null;
            if (data.getInt(i) != 0) {
                for (TileSet tileSet : this.tileSets) {
                    if (tileSet.getTile(data.getInt(i) - 1) != null) {
                        tile = tileSet.getTile(data.getInt(i) - 1);
                    }
                }
                graphics.drawImage(tile, (tile.getWidth() * (i % width)), tile.getHeight() * (int) (i / width), null);
            }
        }
    }
}
