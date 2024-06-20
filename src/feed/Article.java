package feed;

public class Article {
    private String title;
    private String description;
    private String link;
    private String pubDate;

    public Article(String title, String description, String link, String pubDate){
        super();
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    public void print() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Link: " + link);
        System.out.println("Publication Date: " + pubDate);
        System.out.println("*".repeat(80));
    }

    //Get
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLink() {
        return link;
    }
    public String getPubDate() {
        return pubDate;
    }

    //Set
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}