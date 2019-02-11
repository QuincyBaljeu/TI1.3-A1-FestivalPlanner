package GUI.ManageViewer;

import Data.Performance;

import java.util.List;

public class PerformanceManageView  extends ManageView<Performance> implements ManageViewInterface {
    public PerformanceManageView(List<Performance> manageAbeles) {
        super(manageAbeles);
    }

    @Override
    public List<String> getVariables() {
        return null;
    }

    @Override
    public List<String> getVariableTypes() {
        return null;
    }
}
