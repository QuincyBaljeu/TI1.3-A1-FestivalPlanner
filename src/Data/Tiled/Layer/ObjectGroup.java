package Data.Tiled.Layer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class ObjectGroup implements Layer {
	private String name;
	private double opacity;
	private boolean visible;
	private TiledObject[] objects;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getOpacity() {
		return opacity;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public ObjectGroup(JsonObject jsonSource) throws Exception {
		this.name = jsonSource.getString("name");
		this.opacity = jsonSource.getJsonNumber("opacity").doubleValue();
		this.visible = jsonSource.getBoolean("visible");
		try {
			JsonArray jsonObjects = jsonSource.getJsonArray("objects");
			this.objects = new TiledObject[jsonObjects.size()];
			for (int i = 0; i < jsonObjects.size(); i++) {
				JsonValue jsonObject = jsonObjects.get(i);
				this.objects[i] = new TiledObject((JsonObject)jsonObject);
			}
		} catch (Exception ex) {
			throw new Exception("Failed to read objectgroup object array", ex);
		}
	}
	public TiledObject[] getObjects() {
		return objects;
	}

	public void generateFlowMap(TileLayer collisionLayer) {
		int[][] data2D = new int[collisionLayer.getWidth()][collisionLayer.getHeight()] ;

		for (int i = 0; i < collisionLayer.getData().length; i++) {
			int tile = collisionLayer.getData()[i];
			data2D[i % collisionLayer.getWidth()][ i / collisionLayer.getHeight()] = tile;
		}

		for (TiledObject location : this.objects) {
			location.generateFlowMap(collisionLayer, data2D);
		}
	}

	public TiledObject getObject(String objectName) {
		for (TiledObject tiledObject : this.objects) {
			if (tiledObject.getName().equals(objectName)) {
				return tiledObject;
			}
		}
		return null;
	}

	public TiledObject[] getTiledObjects() {
		return this.objects;
	}
}
