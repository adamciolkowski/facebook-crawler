package eiti.sag.facebookcrawler.model;

import java.util.Collection;

public class Experience {

    private final Collection<Work> works;
    private final Collection<Education> education;

    public Experience(Collection<Work> works, Collection<Education> education) {
        this.works = works;
        this.education = education;
    }

    public Collection<Work> getWorks() {
        return works;
    }

    public Collection<Education> getEducation() {
        return education;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "works=" + works +
                ", education=" + education +
                '}';
    }
}
