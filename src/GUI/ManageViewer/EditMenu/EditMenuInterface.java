package GUI.ManageViewer.EditMenu;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.List;

public interface EditMenuInterface {
    List<String> getVariableTypes();
    List<Node> getInputMethod();
    Button getDoneButton();


}
