package eiti.sag.facebookcrawler.model;

public class FamilyMember {

    private final String link;
    private final String name;
    private final String type;

    public FamilyMember(String link, String name, String type) {
        this.link = link;
        this.name = name;
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
