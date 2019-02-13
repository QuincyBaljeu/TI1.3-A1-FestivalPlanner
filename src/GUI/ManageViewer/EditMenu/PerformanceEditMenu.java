package GUI.ManageViewer.EditMenu;

import Data.Performance;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.List;

public class PerformanceEditMenu extends EditMenu<Performance> {
    public PerformanceEditMenu(Performance object) {
        super();
    }

    @Override
    public List<String> getVariableTypes() {
        return null;
    }

    @Override
    public List<Node> getInputMethod() {
        return null;
    }

    @Override
    public Button getDoneButton() {
        return null;
    }
}
