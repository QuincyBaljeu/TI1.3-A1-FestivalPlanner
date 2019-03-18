package Simulation;

import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.TileLayer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Map {

	private Data.Tiled.Map map;
    private BufferedImage cacheImage;

    public Map(String jsonFile) throws Exception {
    	this.map = new Data.Tiled.Map(jsonFile);
		this.cacheImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
	}

    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.cacheImage, 0, 0, null);
    }

    public void drawCache() {
        Graphics2D graphics2D = this.cacheImage.createGraphics();
        for (Layer layer : this.map.getLayers()) {
            if (layer.isVisible()){
                drawLayer(layer, graphics2D);
            }
        }
    }

    public boolean hasCollision(Visitor visitor) {
        Point2D currentLocation = visitor.getPosition();
        Layer boundaryLayer = layers.get(layers.size() - 1);
        int tileWidth = tileSets.get(0).getWidth();
        int tileHeight = tileSets.get(0).getHeight();


        for (int i = 0; i < boundaryLayer.getData().size(); i++) {
            if (boundaryLayer.getData().getInt(i) != 0) {
                int currColision = boundaryLayer.getData().getJsonNumber(i).intValue();
                if (currColision != 0) {
                    if (currColision == 441) {
//                        System.out.println("Possible col");
                        if ((currentLocation.getX() >= (tileWidth * (i % width)) && currentLocation.getX() <= (tileWidth * (i % width)) +tileWidth)
                                &&
                                (currentLocation.getY() >= tileHeight * (i / width) && currentLocation.getY() <= tileHeight * (i / width) + tileHeight)
                        ) {
                            System.out.println("return true " +
                                    currColision);
                            return true;
                        }
                    }
                }
            }


//        for (int x = 0; x < width-1; x++) {
//            for (int y = 0; y < height-1; y++) {
//                int currColision = boundaryLayer.getData().getJsonNumber(x*width+y).intValue();
//                if (currColision != 0) {
//                    if (currColision == 441) {
////                        System.out.println("Possible col");
//                        if ((currentLocation.getX()>= x * tileWidth && currentLocation.getX() <= x *  + tileWidth)
//                                &&
//                                (currentLocation.getY()>= y && currentLocation.getY() <= y + tileHeight)
//                        ){
//                            System.out.println("return true " +
//                                    currColision);
//                            return true;
//                        }
//                }
//
//
////                    Area area = new Area(new Rectangle2D.Double(x* tileSets.get(0).getWidth(), y*tileSets.get(0).getHeight(), tileSets.get(0).getWidth(), tileSets.get(0).getHeight()));
////                    if (area.contains(currentLocation)) {
////                        //System.out.println("Simulation.Visitor " + visitor.getPosition() + " has collision with: " + area);
////                        return true;
////                    }
////
//                }
//            }
//        }
        }
        return false;
    }

    private void drawLayer(Layer layer, Graphics2D graphics) {
    	if (!(layer instanceof TileLayer)){
    		return;
		}
        int[] data = ((TileLayer)layer).getData();

        for (int i = 0; i < data.length; i++) {
            BufferedImage tile = this.map.getTiles()[data[i]];
            if (data[i] != 0 && tile != null) {
                graphics.drawImage(tile, (tile.getWidth() * (i % this.map.getHeight())), tile.getHeight() * (int) (i / this.map.getWidth()), null);
            }
        }
    }
}
