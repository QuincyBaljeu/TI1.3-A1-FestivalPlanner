package GUI.ManageViewer;

import Data.Performance;

import java.util.ArrayList;
import java.util.List;

public class PerformanceManageView  extends ManageView<Performance> {

    public PerformanceManageView(List<Performance> manageAbeles) {
        super(manageAbeles);
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        for (Performance performance : getManageAbeles()) {
            variables.add(performance.getStage().getName() + "#" + performance.getArtists() + "#" + performance.getStartTime() + "#" + performance.getEndTime());
        }
        return variables;
    }

    @Override
    public List<String> getVariableTypes() {
        ArrayList<String> varTypes = new ArrayList<>();
        varTypes.add("Stage");
        varTypes.add("Artist");
        varTypes.add("StartTime");
        varTypes.add("EndTime");
        return varTypes;
    }
}
