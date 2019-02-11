package GUI.ManageViewer;

import Data.Artist;

import java.util.List;

public class ArtistManageView extends ManageView<Artist> implements ManageViewInterface{

    public ArtistManageView(List<Artist> artists) {
        super(artists);
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
