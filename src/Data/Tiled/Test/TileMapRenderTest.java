package Data.Tiled.Test;

import Data.Tiled.JsonMapper;
import Data.Tiled.TileMap;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TileMapRenderTest extends Application {

	public static void Main(String[] args){
		launch(TileMapRenderTest.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TileMap map = new TileMap(
			FileDialogHelper.showOpenFileDialog(primaryStage, "Tiled Files (*.json)", "*.json")
		);
		
		TileMap.TileSet[] tileSets = map.getTilesets();
		for (TileMap.TileSet tileSet : tileSets){
			int firstGid = tileSet.getFirstgid();
			String imageFilePath = tileSet.getSource();
		}

		TileMap.Layer[] layers = map.getLayers();
		for (TileMap.Layer layer : layers){
			int[] mapData = layer.getData();
			Point2D position =
				new Point2D.Double(
					layer.getX(),
					layer.getY()
				);
			String layerName = layer.getName();
			String layerType = layer.getType();
			int numberOfHorizontalTiles = layer.getWidth();
			int numberOfVerticalTiles = layer.getHeight();
		}

		int numberOfHorizontalTiles = map.getWidth();
		int numberOfVerticalTiles = map.getHeight();

		int tileHeight = map.getTileheight();
		int tileWidth = map.getTilewidth();

	}
}
