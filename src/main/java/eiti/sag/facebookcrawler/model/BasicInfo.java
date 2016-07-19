package eiti.sag.facebookcrawler.model;

import java.util.List;

public class BasicInfo {

    private final String birthday;
    private final String gender;
    private final List<String> languages;

    public BasicInfo(String birthday, String gender, List<String> languages) {
        this.birthday = birthday;
        this.gender = gender;
        this.languages = languages;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getLanguages() {
        return languages;
    }
}
