package Data.Tiled.Test;

import Data.Tiled.Tilemap;
import Data.Tiled.TilemapInfo;
import javafx.application.Application;
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		TilemapInfo mapInfo = new TilemapInfo(
			FileDialogHelper.showOpenFileDialog(primaryStage, "Tiled Files (*.json)", "*.json")
		);

		this.map = new Tilemap(mapInfo);

		TilemapInfo.Layer[] layers = mapInfo.getLayers();
		this.data = layers[0].getData();

		BorderPane mainPane = new BorderPane();
		ResizableCanvas canvas = new ResizableCanvas(g -> draw(g), mainPane);
		mainPane.setCenter(canvas);
		draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setTitle("Ying Yang");
		primaryStage.show();
	}

	public void draw(FXGraphics2D g) {
		for (int i = 0; i < this.data.length; i++){
			BufferedImage tile = this.map.getTile(this.data[i]);
			AffineTransform af = new AffineTransform();
			af.translate(i*10, i*10);
			g.drawImage(
				tile,
				af,
				null
			);
		}
	}
}
