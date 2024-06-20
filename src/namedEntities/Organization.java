package namedEntities;
import java.util.List;

public class Organization extends NamedEntity{
    private String creator;

    public Organization (String name, List<String> topic, String creator){
        super(name, topic, CATEGORY.ORGANIZATION);
        this.creator = creator;
    }

    public String getCreator(){
        return creator;
    }

    public void setCreate(String creator){
        this.creator = creator;
    }   

}