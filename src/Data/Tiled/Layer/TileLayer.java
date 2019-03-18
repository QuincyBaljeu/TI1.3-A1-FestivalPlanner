package Data.Tiled.Layer;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class TileLayer implements Layer {
	private int[] data;
	private boolean visible;
	private double opacity;
	private String name;
	private int height;

	@Override
	public String getName() {
		return name;
	}

	private int width;

	public TileLayer(JsonObject JsonSource){
		JsonArray jsonData = JsonSource.getJsonArray("data");
		this.data = new int[jsonData.size()];
		for (int i = 0; i < jsonData.size(); i++) {
			JsonValue jsonValue = jsonData.get(i);
			this.data[i] = ((JsonNumber) jsonValue).intValue();
		}
		this.visible = JsonSource.getBoolean("visible");
		this.name = JsonSource.getString("name");
		this.opacity = JsonSource.getJsonNumber("opacity").doubleValue();
		this.height = JsonSource.getInt("height");
		this.width = JsonSource.getInt("width");
	}

	public int[] getData() {
		return data;
	}

	public boolean isVisible() {
		return visible;
	}

	public double getOpacity() {
		return opacity;
	}
}
