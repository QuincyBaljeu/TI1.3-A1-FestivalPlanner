package GUI.ManageViewer;

import Data.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageManageView extends ManageView<Stage> {

    public StageManageView(List<Stage> manageAbeles) {
        super(manageAbeles);
    }

    @Override
    public List<String> getVariables() {
        List<String>variables = new ArrayList<>();
        for (Stage stage : getManageAbeles()){
            variables.add(stage.getName() + "#" + stage.getPerformances().toString());
        }
        return variables;
    }

    @Override
    public List<String> getVariableTypes() {
        ArrayList<String> varTypes = new ArrayList<>();
        varTypes.add("Name");
        varTypes.add("Performance");
        return varTypes;
    }
}
