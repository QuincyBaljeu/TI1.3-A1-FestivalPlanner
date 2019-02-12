package GUI.ManageViewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StageManageView extends ManageView<Data.Podium> {

    public StageManageView(List<Data.Podium> manageAbeles) {
        super(manageAbeles);
    }

    @Override
    public List<String> getVariables() {
        List<String>variables = new ArrayList<>();
        for (Data.Podium podium : getManageAbeles()){
            variables.add(podium.getName() + "#" + podium.getPerformances().toString());
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
