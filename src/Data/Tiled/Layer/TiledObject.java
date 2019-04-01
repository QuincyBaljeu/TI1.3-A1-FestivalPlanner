package Data.Tiled.Layer;

import Data.Tiled.Map;
import Simulation.Visitor;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.geom.Point2D;
import java.util.List;

public class TiledObject {
	private int height;
	private int width;
	private int x;
	private int y;
	private String name;
	private double rotation;
	private boolean visible;
	private Point2D[] polygon;
	private FlowLayer flowLayer;

	public TiledObject(JsonObject jsonSource){
		this.height = jsonSource.getInt("height");
		this.width = jsonSource.getInt("width");
		this.x = jsonSource.getInt("x");
		this.y = jsonSource.getInt("y");
		this.rotation = jsonSource.getInt("rotation");
		this.name = jsonSource.getString("name");
		this.visible = jsonSource.getBoolean("visible");
		this.flowLayer = null;
		try {
			JsonArray jsonPolygon = jsonSource.getJsonArray("polygon");
			this.polygon = new Point2D[jsonPolygon.size()];
			for (int i = 0; i < jsonPolygon.size(); i++) {
				JsonValue jsonPoint = jsonPolygon.get(i);
				this.polygon[i] = new Point2D.Double(
					((JsonObject) jsonPoint).getInt("x"),
					((JsonObject) jsonPoint).getInt("y")
				);
			}
		}
		catch (Exception ex){}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Point2D getPosition(){
		return new Point2D.Double(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void generateFlowMap(TileLayer collisionLayer, int[][] data2D, List<Visitor> visitors, Map dataMap) {
		this.flowLayer = new FlowLayer(collisionLayer, this, data2D, visitors, dataMap);
	}

	public FlowLayer getFlowLayer() {
		return flowLayer;
	}

	public String getName() {
		return name;
	}
	public double getRotation() {
		return rotation;
	}
	public Point2D[] getPolygon() {
		return polygon;
	}
}
