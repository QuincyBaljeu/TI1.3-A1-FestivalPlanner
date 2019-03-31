package Data.Tiled.Layer;

import javax.json.JsonObject;

public class ImageLayer implements Layer{
	public ImageLayer(JsonObject JsonSource){
		// No one actually uses this...
	}

	public int[] getData() {
		return new int[0];
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public double getOpacity() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}
}
