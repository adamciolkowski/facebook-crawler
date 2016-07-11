package eiti.sag.facebookcrawler;

import eiti.sag.facebookcrawler.accessor.WebDriverFacebookAccessor;
import eiti.sag.facebookcrawler.repository.FacebookUserRepository;
import eiti.sag.facebookcrawler.repository.json.JsonFacebookUserRepository;
import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;
import eiti.sag.facebookcrawler.visitor.SavingFacebookUserVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if(args.length != 2)
            throw new IllegalArgumentException("No Facebook email, password or username given");
        new Main().run(args[0], args[1], args[2]);
    }

    private void run(String email, String password, String username) {
        WebDriver webDriver = new FirefoxDriver();
        WebDriverFacebookAccessor accessor = new WebDriverFacebookAccessor(webDriver);
        accessor.login(email, password);
        File directory = new File(System.getProperty("user.home"), "facebook-users");
        FacebookUserRepository repository = new JsonFacebookUserRepository(directory);

        FacebookUserVisitor visitor = new SavingFacebookUserVisitor(repository);
        FacebookCrawler crawler = new FacebookCrawler(accessor, visitor);
        crawler.loadRecursively(username);

        accessor.logout();
    }

}
