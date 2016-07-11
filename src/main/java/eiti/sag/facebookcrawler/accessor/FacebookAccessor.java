package eiti.sag.facebookcrawler.accessor;

import eiti.sag.facebookcrawler.model.FacebookUser;

public interface FacebookAccessor {

    FacebookUser fetchUser(String username);

    void logout();
}
