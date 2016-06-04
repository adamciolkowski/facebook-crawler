package eiti.sag.facebookcrawler.model;

import java.util.Collection;

public class Relationships {

    String relationship;

    Collection<FamilyMember> familyMembers;

    public Relationships(Collection<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
