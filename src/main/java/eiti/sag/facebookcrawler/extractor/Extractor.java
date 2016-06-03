package eiti.sag.facebookcrawler.extractor;

import org.openqa.selenium.WebDriver;

public interface Extractor<T> {

    T extract(WebDriver wd);
}
