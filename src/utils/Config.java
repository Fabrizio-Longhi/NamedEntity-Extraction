package utils;

public class Config {
    private boolean printFeed = false;
    private boolean help = false;
    private String computeNamedEntities = null;
    private String feedKey = null;
    private String statsForm = null;

    public Config(boolean help, boolean printFeed, String computeNamedEntities, String feedKey, String statsForm) {
        this.help = help;                   //Agregamos help
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.feedKey = feedKey;
        this.statsForm = statsForm;       //agregamos statsForma
    }

    public boolean getPrintFeed() {
        return printFeed;
    }

    public String getComputeNamedEntities() {
        return computeNamedEntities;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public boolean getHelp() {
        return help;
    }

    public String getStatsForm() {
        return statsForm;
    }

}
