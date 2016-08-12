package eiti.sag.facebookcrawler.model;

public class FamilyMember {

    private final String username;
    private final String name;
    private final String type;

    public FamilyMember(String username, String name, String type) {
        this.username = username;
        this.name = name;
        this.type = type;
    }

    public String getUsername() {
        return username;
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
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
