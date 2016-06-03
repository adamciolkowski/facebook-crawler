package eiti.sag.facebookcrawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.accessor.WebDriverFacebookAccessor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        accessor.logout();
    }

}
