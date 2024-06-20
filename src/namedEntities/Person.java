package namedEntities;
import java.util.List;

public class Person extends NamedEntity{
    private String birthDate;

    public Person(String name, List<String> topic, String birthDate){
        super(name, topic, CATEGORY.PERSON);   
        this.birthDate = birthDate;
    }
    
    public String getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
}
