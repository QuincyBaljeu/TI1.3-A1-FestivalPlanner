package Data.Tiled.Test;

import Data.Tiled.Layer;
import Data.Tiled.Tilemap;
import Data.Tiled.TilemapInfo;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class TileMapRenderTest extends Application {

	public static void Main(String[] args){
		launch(TileMapRenderTest.class);
	}

	private int[] data;
	private Tilemap map;
	private Layer[] layers;

	@Override
	public void start(Stage primaryStage) throws Exception {
		TilemapInfo mapInfo = new TilemapInfo(
			FileDialogHelper.showOpenFileDialog(primaryStage, "Tiled Files (*.json)", "*.json")
		);

		this.map = new Tilemap(mapInfo);
		layers = this.map.getLayers();

		BorderPane mainPane = new BorderPane();
		ResizableCanvas canvas = new ResizableCanvas(g -> draw(g), mainPane);
		mainPane.setCenter(canvas);
		draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setTitle("Ying Yang");
		primaryStage.show();
	}

	public void draw(FXGraphics2D g) {
		for(Layer layer : layers){
			layer.draw(g);
		}
	}
}
