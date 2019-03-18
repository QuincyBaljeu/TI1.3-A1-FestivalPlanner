package Data.Tiled.test;

import Data.Tiled.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class TestMap extends Application {

	public static void Main(String[] args){
		launch(TestMap.class);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		Map map = new Map(
			FileDialogHelper.showOpenFileDialog(primaryStage, "Tiled Files (*.json)", "*.json")
		);

		BorderPane mainPane = new BorderPane();
		ResizableCanvas canvas = new ResizableCanvas(g -> draw(g), mainPane);
		mainPane.setCenter(canvas);
		draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.setTitle("Ying Yang");
		primaryStage.show();
	}

	public void draw(FXGraphics2D g) {

	}
}
