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
        Layer boundryLayer = this.map.getLayers().get(this.map.getLayers().size()-1);
        int tileWidth = this.map.getTileWidth();
        int tileHeight = this.map.getTileHeight();
        for (int x = 0; x < this.map.getWidth()-1; x++) {
            for (int y = 0; y < this.map.getHeight()-1; y++) {
                int currColision = ((TileLayer)boundryLayer).getData()[x*this.map.getWidth()+y];
                if (currColision == 441) {
                    if (
                        (visitor.getPosition().getX()>= x && visitor.getPosition().getX() <= x + tileWidth)
                        &&
                        (visitor.getPosition().getY()>= y && visitor.getPosition().getY() <= y + tileHeight)
                    ){
                        return true;
                    }
                }
            }
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
