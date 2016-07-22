package eiti.sag.facebookcrawler.accessor.webdriver.extractor;

import org.openqa.selenium.WebDriver;

public interface Extractor<T> {

    T extract(WebDriver wd);
}
