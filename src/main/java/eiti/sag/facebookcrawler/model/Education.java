package eiti.sag.facebookcrawler.model;

public class Education {

    private final String place;
    private final String link;

    public Education(String place, String link) {
        this.place = place;
        this.link = link;
    }

    public String getPlace() {
        return place;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Education{" +
                "place='" + place + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
