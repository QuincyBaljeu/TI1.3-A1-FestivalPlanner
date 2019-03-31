package Simulation;

import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.TileLayer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Map {

	private Data.Tiled.Map map;
    private BufferedImage cacheImage;

    public Map(Data.Tiled.Map map) throws Exception {
    	this.map = map;
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
		Layer boundaryLayer = this.map.getLayers().get(this.map.getLayers().size()-2);

        Point2D currentLocation = visitor.getPosition();
        int tileWidth = this.map.getTileWidth();
        int tileHeight = this.map.getTileHeight();

        for (int i = 0; i < ((TileLayer)boundaryLayer).getData().length; i++) {
            if (((TileLayer) boundaryLayer).getData()[i] != 0) {
                int currColision = ((TileLayer) boundaryLayer).getData()[i];
                if (currColision != 0) {
                    if (currColision == 441) {
                        if ((currentLocation.getX() >= (tileWidth * (i % this.map.getWidth())) && currentLocation.getX() <= (tileWidth * (i % this.map.getWidth())) +tileWidth)
                                &&
							(currentLocation.getY() >= tileHeight * (i / this.map.getWidth()) && currentLocation.getY() <= tileHeight * (i / this.map.getWidth()) + tileHeight)
                        ) {
                            //System.out.println("return true " + currColision);
                            return true;
                        }
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
