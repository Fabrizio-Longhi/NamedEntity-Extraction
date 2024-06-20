package namedEntities.heuristics;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;

public class DictHeuristic extends Heuristics{
    public String getName() {
        return "DictHeur";
    }

    public String getDescription() {
        return "Extracts ONLY entities that are part of the dictionary (only their labels).";
    }

     public List<String> extractCandidates(String text) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get("src/data/dictionary.json")));
            JSONArray jsonArray = new JSONArray(jsonData);
            List<String> candidates = new ArrayList<>();
            text = text.replaceAll("[-+.^:,\"]", "");
            text = Normalizer.normalize(text, Normalizer.Form.NFD);

            text = text.replaceAll("\\p{M}", "");
            for (String word: text.split(" ")){
                for (int i = 0; i < jsonArray.length();++i){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String label = jsonObject.getString("label");
                    if (label.equals(word)){
                        candidates.add(word);
                    }
                }
            }
            return candidates;
        }
        catch (Exception e){
            return new ArrayList<>();
        }

        
    }
}
