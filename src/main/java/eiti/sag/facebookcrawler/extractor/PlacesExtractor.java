package eiti.sag.facebookcrawler.extractor;

import eiti.sag.facebookcrawler.model.Location;
import eiti.sag.facebookcrawler.model.Places;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PlacesExtractor extends PageletExtractor<Places> {

    public Places extract(WebDriver wd) {
        WebElement pagelet = getPagelet(wd, "hometown");
        Location currentCity = fetchLocation(pagelet, "current_city");
        Location hometown = fetchLocation(pagelet, "hometown");
        return new Places(currentCity, hometown);
    }

    private Location fetchLocation(WebElement pagelet, String id) {
        List<WebElement> e = pagelet.findElements(By.id(id));
        if (e.isEmpty())
            return null;
        return doFetchLocation(e.get(0));
    }

    private Location doFetchLocation(WebElement webElement) {
        WebElement a = webElement.findElement(By.tagName("a"));
        String name = a.getText();
        String link = a.getAttribute("href");
        return new Location(name, link);
    }

}
