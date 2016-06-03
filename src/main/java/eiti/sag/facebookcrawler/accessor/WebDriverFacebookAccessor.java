package eiti.sag.facebookcrawler.accessor;

import eiti.sag.facebookcrawler.model.FacebookUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebDriverFacebookAccessor implements FacebookAccessor {

    private static final String BASE_URL = "https://www.facebook.com/";

    private final WebDriver webDriver;

    public WebDriverFacebookAccessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void login(String email, String password) {
        webDriver.get(BASE_URL);
        webDriver.findElement(By.id("email")).sendKeys(email);
        webDriver.findElement(By.id("pass")).sendKeys(password);
        webDriver.findElement(By.id("loginbutton")).click();
    }

    @Override
    public FacebookUser fetchUser(String username) {
        webDriver.get(BASE_URL + username);
        FacebookUser user = new FacebookUser();
        user.setName(fetchName());
        return user;
    }

    private String fetchName() {
        return webDriver.findElement(By.id("fb-timeline-cover-name")).getText();
    }

    @Override
    public void logout() {
        webDriver.close();
    }
}
