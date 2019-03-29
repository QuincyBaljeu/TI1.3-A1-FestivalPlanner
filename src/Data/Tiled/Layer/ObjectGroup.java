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

	public ObjectGroup(JsonObject jsonSource){
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
			new Exception("Failed to read objectgroup object array", ex);
		}
	}

	public void generateFlowMap() {
		for (TiledObject location : this.objects) {
			location.generateFlowMap();
		}
	}
}
