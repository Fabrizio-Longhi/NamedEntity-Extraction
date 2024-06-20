import feed.Article;
import feed.FeedParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import namedEntities.*;
import namedEntities.heuristics.*;
import utils.Config;
import utils.FeedsData;
import utils.JSONParser;
import utils.Par;
import utils.UserInterface;


public class App {
    
    // ERROR CODES
    private
    static final int SUCCESS = 0;
    static final int INTERNAL_ERROR = 1;
    static final int BAD_ARGS = 2;
    static final int JSON_EXCEPTION = 3;
    
    
    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();         //crea un array de feedsData

        
        try {
            //parsea el json de feedsData
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<Heuristics> heuristicsArray = new ArrayList<>();
        try {
            heuristicsArray = Heuristics.getHeuristics();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        
        Config config = ui.handleInput(args);
        //Para ver si se llama a help
        if(config.getHelp()){
            printHelp(feedsDataArray, heuristicsArray);
            System.exit(0);
        } else{
            int check = run(config, feedsDataArray, heuristicsArray, "src/data/dictionary.json");
            switch(check){
                case INTERNAL_ERROR:
                    System.out.println("Internal error.");
                    System.exit(1);
                    break;
                case BAD_ARGS:
                    System.out.println("Bad arguments: please use -h or --help to see the available options.");
                    System.exit(1);
                    break;
                case JSON_EXCEPTION:
                    System.out.println("JSON exception: check the JSON dictionary.");
                    System.exit(1);
                    break;
                case SUCCESS:
                    System.out.println("Success!");
                    System.exit(0);
                    break;
            }
        }
    }



    private static int run(Config config, List<FeedsData> feedsDataArray, List<Heuristics> heuristicsArray, String dictFilePath) {

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return INTERNAL_ERROR;
        }

        List<Article> allArticles = new ArrayList<>();
        for (int i = 0; i < feedsDataArray.size(); ++i) {
            FeedsData feed = feedsDataArray.get(i);
            //si feedKey es null o es igual al label del feed
            if(config.getFeedKey() == null || config.getFeedKey().equals(feed.getLabel())){
                try {
                    String data = FeedParser.fetchFeed(feed.getUrl());      //fetchFeed regresa un string del XML
                    List<Article> articles = FeedParser.parseXML(data);     //parseXML regresa una lista de articulos
                    allArticles.addAll(articles);                           //agrega todos los articulos a la lista allArticles
                } catch (Exception e) {
                    e.printStackTrace();
                    return JSON_EXCEPTION;
                }
            }
        }

        if (config.getPrintFeed()) {
            System.out.println("Printing feed(s) ");
            
            //imprime todos los articulos
            for (Article article : allArticles) {       
                article.print();    
            }
        }

        if (config.getComputeNamedEntities() != null) {

            List <String> stringEntityList = new ArrayList<>();
            
            //itera sobre los heuristics
            boolean foundHeuristic = false;
            for(int i = 0; i < heuristicsArray.size(); i++){
                //obtiene el nombre del heuristic
                Heuristics currentHeuristic = heuristicsArray.get(i);    
                if(config.getComputeNamedEntities().equals(currentHeuristic.getName())) {
                    System.out.println("Computing named entities using " + currentHeuristic.getName());
                    foundHeuristic = true;
                    for(Article article : allArticles){ 
                        //agrega los candidato que satisfacen la herusticas a stringEntityList
                        stringEntityList.addAll(currentHeuristic.extractCandidates(article.getDescription()));
                        stringEntityList.addAll(currentHeuristic.extractCandidates(article.getTitle()));
                    }
                    break;
                }
            }
            //si no se encontro el heuristic
            if(!foundHeuristic){
                System.out.println("Invalid heuristic name");
                return BAD_ARGS;
            }

            
            List <NamedEntity> entityList = new ArrayList<>();                  //crea una lista de NamedEntity
            for(String namedEntity : stringEntityList){
                try{
                //busca y crea la NamedEntity
                NamedEntity entity = JSONParser.searchAndCreate(dictFilePath, namedEntity);
                entityList.add(entity);
                } catch (IOException e){
                    e.printStackTrace();
                    return JSON_EXCEPTION;
                }
            }

            if(config.getStatsForm() != null){

                List<Par> iterationList = new ArrayList<>();  //lista de pares (NamedEntity, Int)

                for(NamedEntity nEntity : entityList){
                    boolean foundIteration = false;
                    //itera sobre la lista de pares
                    for(Par currentPar : iterationList){
                        //si el nombre de la NamedEntity es igual al nombre de la NamedEntity del par
                        if(((NamedEntity)currentPar.getFirst()).getName().equals(nEntity.getName())){
                            currentPar.setSecond((int)currentPar.getSecond() + 1);
                            foundIteration = true;
                            break;
                        }
                    }
                    //si no se encontro la NamedEntity en la lista de pares
                    if(!foundIteration){
                        iterationList.add(new Par(nEntity, 1));
                    }
                }

                //imprime las estadisticas en la forma categoria
                if(config.getStatsForm().equals("cat")){
                    System.out.println("Printing category-wise stats");
                    List<Par> catList = new ArrayList<>();  // Lista < Par (CATEGORY, List< Par(NamedEntity, Int) > ) >
                    for (Par iterationPar : iterationList) {
                        CATEGORY category = ((NamedEntity) iterationPar.getFirst()).getCategory();
                        boolean foundCat = false;

                        for (Par catPar : catList) {
                            if (((CATEGORY) catPar.getFirst()).equals(category)) {
                                ((ArrayList<Par>)catPar.getSecond()).add(iterationPar);
                                foundCat = true;
                                break;
                            }
                        }
                        if(!foundCat){
                            //crea un nuevo par con la categoria y la lista de pares
                            Par newPar = new Par(category, new ArrayList<Par>());
                            ((ArrayList<Par>)newPar.getSecond()).add(iterationPar);
                            catList.add(newPar);

                        }
                    }
                    for(Par catPar : catList){ // par(CATEGORY, List< par(NamedEntity, Int) > )
                        System.out.println(catPar.getFirst() + ":");
                        for(Par innerPar : (ArrayList<Par>)catPar.getSecond()){
                            System.out.println("      " + ((NamedEntity)innerPar.getFirst()).getName() + ": " + "(" + innerPar.getSecond() + ")");
                        }
                    }
                

                } else if(config.getStatsForm().equals("topic")){
                    System.out.println("Printing topic-wise stats");
                    //imprime las estadisticas por topico

                    List<Par> topList = new ArrayList<>();
                    for (Par par : iterationList) {
                        List<String> topics = ((NamedEntity) par.getFirst()).getTopic();
                        boolean foundTopic = false;
                        for(String topic : topics){

                            for (Par topPar : topList) {
                                if (((String) topPar.getFirst()).equals(topic)) {
                                    ((ArrayList<Par>)topPar.getSecond()).add(par);
                                    foundTopic = true;
                                    break;
                                }
                            }
                            if(foundTopic == false){
                                Par newPar = new Par(topic, new ArrayList<Par>());
                                ((ArrayList<Par>)newPar.getSecond()).add(par);
                                topList.add(newPar);

                            }
                        }
                    }
                    for(Par par : topList){ // par(String , List< par(NamedEntity, Int) > )
                        System.out.println(par.getFirst() + ":");
                        for(Par innerPar : (ArrayList<Par>)par.getSecond()){
                            System.out.println("      " + ((NamedEntity)innerPar.getFirst()).getName() + ": " + "(" + innerPar.getSecond() + ")");
                        }
                    }

                } else {
                    System.out.println("Invalid stats format");
                    return BAD_ARGS;
                }
            }
            
        }
        return SUCCESS;
    }

    private static void printHelp(List<FeedsData> feedsDataArray, List<Heuristics> heuristicDataArray) {
        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with");
        System.out.println("                                       the specified key");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");
        for (int i = 0; i < heuristicDataArray.size(); i++) {
            System.out.println("                                       " + heuristicDataArray.get(i).getName() + ": " + heuristicDataArray.get(i).getDescription());
        }

        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats");
        System.out.println("                                       topic: Topic-wise stats");
    }

}
