package eiti.sag.facebookcrawler.model;

public class FacebookUser {

    private String name;
    private Experience experience;

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
}
