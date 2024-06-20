package namedEntities;
import java.util.List;

public class Event extends NamedEntity{
    
    private
    String dateStart;
    String dateEnd;

    public Event(String name, List<String> topic, String dateStart, String dateEnd){
        super(name, topic, CATEGORY.EVENT);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    // Getters
    public String getDateStart(){
        return dateStart;
    }

    public String getDateEnd(){
        return dateEnd;
    }

    // Setters
    public void setDateStart(String dateStart){
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd){
        this.dateEnd = dateEnd;
    }

}
