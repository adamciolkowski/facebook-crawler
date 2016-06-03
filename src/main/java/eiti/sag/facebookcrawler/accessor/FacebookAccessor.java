package eiti.sag.facebookcrawler.accessor;

public interface FacebookAccessor {

    void login(String email, String password);

    void logout();
}
