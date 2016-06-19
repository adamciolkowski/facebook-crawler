package eiti.sag.facebookcrawler.visitor;

import eiti.sag.facebookcrawler.model.FacebookUser;

public interface FacebookUserVisitor {

    boolean shouldVisit(String username);

    void visit(FacebookUser user);

    void onFetchUserFailed(String username, Exception e);
}
