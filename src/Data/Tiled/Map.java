package Data.Tiled;

import Data.Tiled.Layer.ImageLayer;
import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.ObjectGroup;
import Data.Tiled.Layer.TileLayer;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
	private int height;
	private int width;
	private int tileHeight;
	private int tileWidth;
	private List<Layer> layers;
	private BufferedImage[] tiles;

	public Map(String jsonFile) throws IOException {
		String workingDirectory = new File(jsonFile).getParent();
		JsonObject inputObject = this.readJsonFile(jsonFile);
		this.height = inputObject.getInt("height");
		this.width = inputObject.getInt("width");
		this.tileHeight = inputObject.getInt("tileheight");
		this.tileWidth = inputObject.getInt("tilewidth");

		this.tiles = this.readTiles(inputObject, workingDirectory);
		this.layers = readLayers(inputObject);
		generateFlowPaths();
		int x = 69;
	}

	private void generateFlowPaths() {
		for (Layer layer : this.layers) {
			if (layer.getClass() == ObjectGroup.class) {
				ObjectGroup objectGroup = (ObjectGroup) layer;
				objectGroup.generateFlowMap();
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public BufferedImage[] getTiles() {
		return tiles;
	}

	public Layer getCollisionLayer() {
		for (Layer layer : this.layers) {
			if (layer.getName().equals("Collision Layer"))
				return layer;
		}
		return null;
	}

	private JsonObject readJsonFile(String filePath) throws FileNotFoundException{
		return readJsonFile(new File(filePath));
	}

	private JsonObject readJsonFile(File file) throws FileNotFoundException{
		System.out.println(file.getPath());
		return Json.createReader(
			new FileInputStream(
				file
			)
		).readObject();
	}

	private List<Layer> readLayers(JsonObject inputObject){
		List<Layer> layers = new ArrayList<>();
		JsonArray JsonLayers = inputObject.getJsonArray("layers");
		for (JsonValue JsonLayer : JsonLayers){
			switch (((JsonObject)JsonLayer).getString("type")){
				case "tilelayer": layers.add(new TileLayer((JsonObject)JsonLayer)); break;
				case "objectgroup": layers.add(new ObjectGroup((JsonObject)JsonLayer)); break;
				case "imagelayer": layers.add(new ImageLayer((JsonObject)JsonLayer)); break;
			}
		}
		return layers;
	}

	private BufferedImage[] readTiles(JsonObject inputObject, String workingDirectory) throws IOException{
		List<Tileset> tilesets = this.readTilesets(inputObject, workingDirectory);
		int lastGid = 0;
		for (Tileset tileset : tilesets){
			if (lastGid < tileset.lastGid){
				lastGid = tileset.lastGid;
			}
		}
		BufferedImage[] tileList = new BufferedImage[lastGid+1];
		for (Tileset tileset : tilesets){
			BufferedImage[] tiles = tileset.getTiles();
			for (int i = tileset.firstGid; i < tileset.lastGid; i++) {
				tileList[i] = tiles[i-tileset.firstGid];
			}
		}
		return tileList;
	}

	private List<Tileset> readTilesets(JsonObject inputObject, String workingDirectory) throws FileNotFoundException{
		JsonArray JsonTilesets = inputObject.getJsonArray("tilesets");
		List<Tileset> tilesets = new ArrayList<Tileset>();
		for (JsonValue JsonTileset : JsonTilesets){
			tilesets.add(new Tileset(((JsonObject)JsonTileset), workingDirectory));
		}
		return tilesets;
	}

	private class Tileset {
		private int firstGid;
		private int lastGid;
		private File source;
		private JsonObject JsonSource;

		public Tileset(JsonObject tileset, String workingDirectory) throws FileNotFoundException{
			this.source = new File(workingDirectory, tileset.getString("source"));
			this.JsonSource = readJsonFile(this.source);
			this.firstGid = tileset.getInt("firstgid");
			this.lastGid = this.firstGid + this.JsonSource.getInt("tilecount");
		}

		public BufferedImage[] getTiles() throws IOException{
			return this.cutImage(
				new File(this.source.getParent(), this.JsonSource.getString("image")),
				this.JsonSource.getInt("imageheight")/this.JsonSource.getInt("tileheight"),
				this.JsonSource.getInt("columns")
			);
		}

		private BufferedImage[] cutImage(File imageFile, int tilesHorizontal, int tilesVertical) throws IOException {
			BufferedImage image;
			try {
				 image = ImageIO.read(imageFile);
			}
			catch (Exception ex){
				// Check the filepath in the json file
				throw ex;
			}
			if (image == null){
				// Only Jpeg, png, bmp, wbmp and gif are supported
				throw new NullPointerException("Image file not recognized");
			}
			int tileWidth = image.getWidth() / tilesHorizontal;
			int tileHeight = image.getHeight() / tilesVertical;
			int tileCount = tilesHorizontal * tilesVertical;

			BufferedImage[] cuttedTiles = new BufferedImage[tileCount];

			for (int i = 0; i < tileCount; i++) {
				cuttedTiles[i] = image.getSubimage(tileWidth * (i % tilesHorizontal), tileHeight * (i / tilesVertical), tileWidth, tileHeight);
			}

			return cuttedTiles;
		}
	}
}
