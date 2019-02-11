package GUI.ManageViewer;

import Data.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistManageView extends ManageView<Artist> {

    public ArtistManageView(List<Artist> artists) {
        super(artists);
    }

    @Override
    public List<String> getVariables() {
        List<String> variables = new ArrayList<>();
        for (Artist artist : getManageAbeles()) {
            variables.add(artist.getName() + "#" + artist.getGenre().toString() + "#" + artist.getCountry());
        }
        return variables;
    }

    @Override
    public List<String> getVariableTypes() {
        ArrayList<String> varTypes = new ArrayList<>();
        varTypes.add("Name");
        varTypes.add("Genre");
        varTypes.add("Country of Origin");
        return varTypes;
    }
}
 