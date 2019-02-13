package GUI.ManageViewer.EditMenu;

import Data.Podium;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.List;

public class StageEditMenue extends EditMenu<Podium>{
    private Podium podium;
    public StageEditMenue(Podium object) {
        super();
        this.podium = object;
        super.initScene();
    }

    public StageEditMenue() {
        super();
        super.initScene();
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
