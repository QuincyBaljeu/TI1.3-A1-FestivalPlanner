package Simulation;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;

public class Layer {

    private JsonArray data;
    private Boolean visible;
    private String name;
    private float opacity;

    private int height;
    private int width;

    public Layer(JsonObject jsonLayer) {
        this.data = jsonLayer.getJsonArray("data");
        this.visible = jsonLayer.getBoolean("visible");
        this.name = jsonLayer.getString("name");
        this.opacity = jsonLayer.getInt("opacity");
        this.height = jsonLayer.getInt("height");
        this.width = jsonLayer.getInt("width");
    }

    public Boolean getVisible() {
        return visible;
    }

    public JsonArray getData() {
        return data;
    }

    public float getOpacity() {
        return opacity;
    }
}
