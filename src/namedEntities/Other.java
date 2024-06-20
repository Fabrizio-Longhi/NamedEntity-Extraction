package namedEntities;
import java.util.List;

public class Other extends NamedEntity{

    public Other(String name, List<String> topic){
        super(name, topic, CATEGORY.OTHER);
    }
}
