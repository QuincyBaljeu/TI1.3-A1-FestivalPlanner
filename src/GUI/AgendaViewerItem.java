package GUI;

import Data.Artist;
import Data.FestivalDay;
import Data.Performance;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.swing.text.TableView;
import java.time.LocalTime;
import java.util.List;

/**
 * Contains the code to generate the text displayed in the horizontal agenda in the viewer and editor
 */
public class AgendaViewerItem {
	public final SimpleStringProperty tableDisplay = new SimpleStringProperty();
    private Performance performance;
    private FestivalDay festivalDay;

    public AgendaViewerItem(Performance performance, FestivalDay festivalDay) {
		this.performance = performance;
        this.festivalDay = festivalDay;
    }

    public Performance getPerformance() {
        return performance;
    }

    public FestivalDay getFestivalDay() {
        return festivalDay;
    }

	public StringProperty getTableDisplay(String text) {
    	this.tableDisplay.set(
    		this.getValueForTime(
    			text
			)
		);
		return tableDisplay;
	}

	public String getValueForTime(String text){
        int hour = Integer.valueOf(text.substring(0, 2));
        if (hour < this.performance.getStartTime().getHour() | hour >  this.performance.getEndTime().getHour()){
            return "";
        }
        int index = hour - this.performance.getStartTime().getHour();
        if (index == 0){
            return this.performance.getPodium().getName() + ": ";
        }
        else {
            int artistsIndex = index -1;
            List<Artist> artists = performance.getArtists();
            if (artistsIndex < artists.size()){
                return artists.get(artistsIndex).getName();
            }
        }
        return "#######";
    }

}
