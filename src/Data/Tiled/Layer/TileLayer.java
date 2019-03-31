package Data.Tiled.Layer;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class TileLayer implements Layer {
	private int[] data;
	private int[][] data2D;
	private boolean visible;
	private double opacity;
	private String name;
	private int height;
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
		this.data2D = new int[this.width][this.height];
		for (int y = 0; y < this.width; y++) {
			for (int x = 0; x < this.height; x++) {
				data2D[x][y] = data[(y * this.width) + x];
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public int[] getData() {
		return data;
	}

	public int[][] getData2D(){
		return data2D;
	}

	public boolean isVisible() {
		return visible;
	}

	public double getOpacity() {
		return opacity;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
