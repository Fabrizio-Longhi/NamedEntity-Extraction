package namedEntities.heuristics;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PairHeuristic extends Heuristics{
    public String getName(){
        return "PairHeuristic";
    }
    public String getDescription(){
        return "Extracts words in pairs from the text";
    }

    public List<String> extractCandidates(String text) {
        List<String> candidates = new ArrayList<>();

        text = text.replaceAll("[-+.^:,\"]", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{M}", "");

        Pattern pattern = Pattern.compile("\\b \\w+ \\b");

        Matcher matcher = pattern.matcher(text);   

        while (matcher.find()) {
            String word = matcher.group();
            if(word.length() % 2 == 0){
                candidates.add(word);
            }
        }
        return candidates;
    }
}
