package eiti.sag.facebookcrawler.model;

public class Location {

    private final String name;
    private final String link;

    public Location(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Location{name='" + name + '\'' + ", link='" + link + "'}";
    }
}
