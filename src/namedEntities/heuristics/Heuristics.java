package namedEntities.heuristics;


//import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public abstract class Heuristics {
    public abstract String getName();
    public abstract String getDescription();

    public abstract List<String> extractCandidates(String text);

    public static List<Heuristics> getHeuristics(){
        List<Heuristics> heuristics = new ArrayList<>();
        heuristics.add(new CapitalizedWordHeuristic());
        heuristics.add(new NameSurnameHeuristic());
        heuristics.add(new DictHeuristic());
        heuristics.add(new PairHeuristic());
        return heuristics;

    }
}