package eiti.sag.facebookcrawler.model;

public class Work {

    private final String workingPlace;
    private final String link;

    public Work(String workingPlace, String link) {
        this.workingPlace = workingPlace;
        this.link = link;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workingPlace='" + workingPlace + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
