package GUI.ManageViewer;

import Data.Artist;

import java.util.List;

public class ArtistManageView extends ManageView<Artist> implements ManageViewInterface{

    public ArtistManageView(List<Artist> artists) {
        super(artists);
    }
}
