package eiti.sag.facebookcrawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.accessor.WebDriverFacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.repository.FacebookUserRepository;
import eiti.sag.facebookcrawler.repository.json.JsonFacebookUserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if(args.length != 2)
            throw new IllegalArgumentException("No Facebook email and password given");
        new Main().run(args[0], args[1]);
    }

    private void run(String email, String password) {
        WebDriver webDriver = new FirefoxDriver();
        FacebookAccessor accessor = new WebDriverFacebookAccessor(webDriver);
        accessor.login(email, password);
        File directory = new File(System.getProperty("user.home"), "facebook-users");
        FacebookUserRepository repository = new JsonFacebookUserRepository(directory);

        FacebookUser user = accessor.fetchUser("zuck");
        repository.save(user);
        accessor.logout();
    }

}
