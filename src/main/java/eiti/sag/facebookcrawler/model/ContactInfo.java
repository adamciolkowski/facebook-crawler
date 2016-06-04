package eiti.sag.facebookcrawler.model;

public class ContactInfo {

    private final BasicInfo basicInfo;

    public ContactInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }
}
