package Data.Tiled.test;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileDialogHelper {
	public static String showOpenFileDialog(Stage stage, String extensionDescription, String ... extensions){
		return showDialog(stage, extensionDescription, true, extensions);
	}
	public static String showSaveFileDialog(Stage stage, String extensionDescription, String ... extensions){
		return showDialog(stage, extensionDescription, false, extensions);
	}
	private static String showDialog(Stage stage, String extensionDescription, boolean isOpenFileDialog, String ... extensions){
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter =
			new FileChooser.ExtensionFilter(
				extensionDescription,
				extensions);
		fileChooser.getExtensionFilters().add(extensionFilter);
		File storFile;
		if (isOpenFileDialog){
			storFile = fileChooser.showOpenDialog(stage);
		}
		else {
			storFile = fileChooser.showSaveDialog(stage);
		}
		if (storFile != null){
			return storFile.getPath();
		}
		else {
			return null;
		}
	}
}
