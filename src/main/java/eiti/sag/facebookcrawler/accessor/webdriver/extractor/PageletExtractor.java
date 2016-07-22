package eiti.sag.facebookcrawler.accessor.webdriver.extractor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class PageletExtractor<T> implements Extractor<T> {

    protected WebElement getPagelet(WebDriver webDriver, String pagelet) {
        return webDriver.findElement(By.id("pagelet_" + pagelet));
    }

    protected WebElement findUlElement(WebElement e) {
        return e.findElement(By.xpath(".//ul[@class='uiList fbProfileEditExperiences _4kg _4ks']"));
    }

    protected String extractInfo(WebElement li) {
        List<WebElement> elements = li.findElements(By.xpath(".//div[@class='fsm fwn fcg']"));
        if(elements.isEmpty())
            return null;
        return elements.get(0).getText();
    }
}
