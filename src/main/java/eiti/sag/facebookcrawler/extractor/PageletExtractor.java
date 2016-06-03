package eiti.sag.facebookcrawler.extractor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class PageletExtractor<T> implements Extractor<T> {

    protected WebElement getPagelet(WebDriver webDriver, String pagelet) {
        return webDriver.findElement(By.id("pagelet_" + pagelet));
    }
}
