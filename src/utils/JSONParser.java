package utils;

import java.io.IOException;
import namedEntities.NamedEntity;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import namedEntities.*;


import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

    static public List<FeedsData> parseJsonFeedsData(String jsonFilePath) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        List<FeedsData> feedsList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            String url = jsonObject.getString("url");
            String type = jsonObject.getString("type");
            feedsList.add(new FeedsData(label, url, type));
        }
        return feedsList;
    }

    static public NamedEntity searchAndCreate(String jsonFilePath, String name) throws IOException {
        // Read JSON file
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // Parse JSON file
        JSONArray jsonArray = new JSONArray(jsonData);

        List<String> topics = new ArrayList<>();
        boolean found = false;

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<String> nameList = JSONArrayToList(jsonObject.getJSONArray("keywords"));   // Get the list of names

            int j = 0;
            // Search for the name in the list of names
            while(!found && j < nameList.size()){
                found = nameList.get(j).toLowerCase().equals(name.toLowerCase());
                j++;
            }
            if(found){
                String category = jsonObject.getString("Category");                 // Get the category
                topics.addAll(JSONArrayToList(jsonObject.getJSONArray("Topics")));  // Get the topics
                String finalName = nameList.get(0);

                // Create the NamedEntity object
                if(category.toLowerCase().equals("person")){
                    return new Person(finalName, topics, "XX/YY/ZZ");
                }
                else if (category.toLowerCase().equals("location")){
                    return new Location(finalName, topics, 0,0);

                }
                else if (category.toLowerCase().equals("organization")){
                    return new Organization(finalName, topics, "XXX YYY");

                }
                else if (category.toLowerCase().equals("event")){
                    return new Event(finalName, topics, "XX/YY/ZZ", "XX/YY/ZZ");
                }
                else {
                    return new Other(finalName, topics);
                }
            }
        }
        // If the name is not found in the JSON file, create an Other object
        topics.add("OTHER");
        Other nEntity = new Other(name, topics);
        return nEntity;
    }

    static public List<String> JSONArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

}
