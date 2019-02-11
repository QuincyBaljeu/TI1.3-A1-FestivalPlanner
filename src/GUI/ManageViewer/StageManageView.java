package GUI.ManageViewer;

import Data.Stage;

import java.util.List;

public class StageManageView extends ManageView<Stage> implements ManageViewInterface{
    public StageManageView(List<Stage> manageAbeles) {
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
