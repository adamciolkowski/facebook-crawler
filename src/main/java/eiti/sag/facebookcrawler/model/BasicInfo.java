package eiti.sag.facebookcrawler.model;

public class BasicInfo {

    private final String birthday;
    private final String gender;

    public BasicInfo(String birthday, String gender) {
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

}
