package eiti.sag.facebookcrawler.model;

public class FacebookUser {

    private String username;
    private String name;
    private Experience experience;
    private Places places;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Experience getExperience() {
        return experience;
    }

    public Places getPlaces() {
        return places;
    }

    public void setPlaces(Places places) {
        this.places = places;
    }
}
