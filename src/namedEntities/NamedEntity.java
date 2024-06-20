package namedEntities;

import java.util.List;


public abstract class NamedEntity {

    private String name; 
    private CATEGORY category;
    private List<String> topic;

    public NamedEntity(String name, List<String> topic, CATEGORY category){
            super();
            this.name = name;
            this.topic = topic;
            this.category = category;
        }

    // Getters    
    public String getName(){
        return name;
    }
    public List<String> getTopic(){
        return topic;
    }
    public CATEGORY getCategory(){
        return category;
    }

    // Setters
    public String setName(String name){
        return this.name = name;
    }
    public List<String> setTopic(List<String> topic){
        return this.topic = topic;
    }
    public CATEGORY setCategory(CATEGORY category){
        return this.category = category;
    }
}

// Las entidades nombradas de diferentes categorías deben tener al menos una característica propia de la categoría