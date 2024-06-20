package namedEntities;
import java.util.List;

public class Location extends NamedEntity{
    private
    float latitude;
    float longitude;

    public Location(String name, List<String> topic, float latitude, float longitude){
        super(name, topic, CATEGORY.LOCATION);
        this.latitude = latitude;
        this.longitude = longitude;   
    }

    public float getLatitude(){
        return latitude;
    }

    public float getLongitude(){
        return longitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

    public void setLongitude(float longitude){
        this.longitude = longitude;
    }
}
