package GUI.ManageViewer.EditMenu;

import Data.Artist;
import Data.Performance;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class PerformanceEditMenu extends EditMenu<Performance> {
    private Performance performance;



    public PerformanceEditMenu(Performance object) {
        super();
        this.performance = object;
        super.initScene();
    }
    public PerformanceEditMenu() {
        super();
        super.initScene();
    }

    @Override
    public List<String> getVariableTypes() {
        ArrayList<String> varTypes = new ArrayList<>();
        varTypes.add("Start time");
        varTypes.add("End time");
        varTypes.add("Start time");
        varTypes.add("Start time");

        return varTypes;
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
