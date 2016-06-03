package eiti.sag.facebookcrawler.accessor;

import eiti.sag.facebookcrawler.model.FacebookUser;

public interface FacebookAccessor {

    void login(String email, String password);

    FacebookUser fetchUser(String username);

    void logout();
}
