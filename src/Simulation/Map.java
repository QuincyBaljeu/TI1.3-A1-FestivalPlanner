package Simulation;

import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.TileLayer;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Map {

	private Data.Tiled.Map map;
    private BufferedImage cacheImage;
    //private ResizableCanvas canvas;

    public Map(Data.Tiled.Map map, ResizableCanvas canvas) throws Exception {
    	this.map = map;
    	//this.canvas = canvas;
    	this.drawCache();
	}

    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.cacheImage, 0, 0, null);
    }

    public Point2D getRequiredCacheSize(){
    	int maxX = 0;
    	int maxY = 0;
		for (Layer layer : this.map.getLayers()) {
			if (layer.isVisible() && layer instanceof TileLayer){
				TileLayer tileLayer = (TileLayer) layer;
				int tileX = map.getTileWidth() * tileLayer.getWidth();
				int tileY = map.getTileHeight() * tileLayer.getHeight();
				if (maxX < tileX){
					maxX = tileX;
				}
				if (maxY < tileY){
					maxY = tileY;
				}
			}
		}
		return new Point2D.Double(maxX, maxY);
	}

    public void drawCache() {
    	Point2D cacheSize = getRequiredCacheSize();
		this.cacheImage = new BufferedImage((int)cacheSize.getX(), (int)cacheSize.getY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = this.cacheImage.createGraphics();
        for (Layer layer : this.map.getLayers()) {
            if (layer.isVisible() && layer instanceof TileLayer){
                drawLayer((TileLayer) layer, graphics2D);
            }
        }
    }

    public boolean hasCollision(Point2D position){
		Layer boundaryLayer = this.map.getLayer("Collision");

		Point2D currentLocation = position;
		int tileWidth = this.map.getTileWidth();
		int tileHeight = this.map.getTileHeight();

		int tileX = (int)currentLocation.getX() / tileWidth;
		int tileY = (int)currentLocation.getY() / tileHeight;

		if (((TileLayer)boundaryLayer).getData2D()[tileX][tileY] > 1){
			return true;
		}

        /*
        for (int i = 0; i < ((TileLayer)boundaryLayer).getData().length; i++) {
            if (((TileLayer) boundaryLayer).getData()[i] != 0) {
                int currColision = ((TileLayer) boundaryLayer).getData()[i];
                if (currColision != 0) {
                    if (currColision == 441) {
                        if ((currentLocation.getX() >= (tileWidth * (i % this.map.getWidth())) && currentLocation.getX() <= (tileWidth * (i % this.map.getWidth())) +tileWidth)
                                &&
							(currentLocation.getY() >= tileHeight * (i / this.map.getWidth()) && currentLocation.getY() <= tileHeight * (i / this.map.getWidth()) + tileHeight)
                        ) {
                            return true;
                        }
                    }
                }
            }
        }
        */
		return false;
	}

    public boolean hasCollision(Visitor visitor) {
    	return this.hasCollision(visitor.getPosition());
    }

    private void drawLayer(TileLayer layer, Graphics2D graphics) {
    	if (!(layer instanceof TileLayer)){
    		return;
		}
    	long[][] data2D = layer.getData2D();

    	graphics.setComposite(
    		AlphaComposite.getInstance(
    			AlphaComposite.SRC_OVER, (float)layer.getOpacity()
			)
		);

    	BufferedImage[] tiles = this.map.getTiles();
		for (int y = 0; y < data2D.length; y++) {
			long[] row = data2D[y];
			for (int x = 0; x < row.length; x++) {
				System.out.println(x + "   max:" + row.length);
				int index = (int)row[x];
				BufferedImage tile = tiles[index];
				if (tile != null){
					graphics.drawImage(
						tile, tile.getWidth()*y, tile.getHeight()*x, tile.getWidth(), tile.getHeight(), null
					);
				}
			}
		}
    }
}
