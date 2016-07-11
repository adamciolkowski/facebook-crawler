package eiti.sag.facebookcrawler.accessor;

import eiti.sag.facebookcrawler.extractor.ContactExtractor;
import eiti.sag.facebookcrawler.extractor.ExperienceExtractor;
import eiti.sag.facebookcrawler.extractor.Extractor;
import eiti.sag.facebookcrawler.extractor.FriendsIdsExtractor;
import eiti.sag.facebookcrawler.extractor.RelationshipsExtractor;
import eiti.sag.facebookcrawler.extractor.PlacesExtractor;
import eiti.sag.facebookcrawler.model.ContactInfo;
import eiti.sag.facebookcrawler.model.Experience;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.model.Places;
import eiti.sag.facebookcrawler.model.Relationships;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Collection;

public class WebDriverFacebookAccessor implements FacebookAccessor {

    private static final String BASE_URL = "https://www.facebook.com/";

    private final WebDriver webDriver;

    private final Extractor<Experience> experienceExtractor = new ExperienceExtractor();
    private final Extractor<Places> placesExtractor = new PlacesExtractor();
    private final Extractor<Relationships> relationshipsExtractor = new RelationshipsExtractor();
    private final Extractor<ContactInfo> contactInfoExtractor = new ContactExtractor();
    private final Extractor<Collection<String>> friendsIdsExtractor = new FriendsIdsExtractor(BASE_URL);

    public WebDriverFacebookAccessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

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
        user.setUsername(username);
        user.setName(fetchName());
        user.setExperience(extract(username, "education", experienceExtractor));
        user.setPlaces(extract(username, "living", placesExtractor));
        user.setRelationships(extract(username, "relationship", relationshipsExtractor));
        user.setContactInfo(extract(username, "contact-info", contactInfoExtractor));
        webDriver.get(BASE_URL + username + "/friends_all");
        user.setFriendsIds(friendsIdsExtractor.extract(webDriver));
        return user;
    }

    private String fetchName() {
        return webDriver.findElement(By.id("fb-timeline-cover-name")).getText();
    }

    private <T> T extract(String username, String section, Extractor<T> extractor) {
        webDriver.get(BASE_URL + username + "/about?section=" + section);
        return extractor.extract(webDriver);
    }

    @Override
    public void logout() {
        webDriver.close();
    }
}
