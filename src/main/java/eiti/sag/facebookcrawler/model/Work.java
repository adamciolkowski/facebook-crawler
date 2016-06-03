package eiti.sag.facebookcrawler.model;

public class Work {

    private final String workingPlace;
    private final String info;
    private final String link;

    public Work(String workingPlace, String info, String link) {
        this.workingPlace = workingPlace;
        this.info = info;
        this.link = link;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public String getInfo() {
        return info;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workingPlace='" + workingPlace + '\'' +
                ", info='" + info + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
