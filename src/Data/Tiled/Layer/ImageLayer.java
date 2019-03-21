package Data.Tiled.Layer;

import javax.json.JsonObject;

public class ImageLayer implements Layer{
	public ImageLayer(JsonObject JsonSource){
		// No one actually uses this...
	}

    @Override
    public boolean isVisible() {
        return false;
    }
}
