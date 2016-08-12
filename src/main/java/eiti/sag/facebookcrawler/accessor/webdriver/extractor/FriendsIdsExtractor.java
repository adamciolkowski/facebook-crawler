package eiti.sag.facebookcrawler.accessor.webdriver.extractor;

import com.google.common.base.Function;
import eiti.sag.facebookcrawler.accessor.util.UsernameParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FriendsIdsExtractor extends PageletExtractor<Collection<String>> {

    private final UsernameParser usernameParser;

    public FriendsIdsExtractor(UsernameParser usernameParser) {
        this.usernameParser = usernameParser;
    }

    @Override
    public Collection<String> extract(WebDriver wd) {
        Wait<WebDriver> wait = new FluentWait<>(wd);
        String xpath = "//ul[@data-pnref='friends']/li[@class='_698']";
        List<WebElement> items = wait.until(allFriendsLoaded(By.xpath(xpath)));
        List<String> ids = new ArrayList<>();
        for (WebElement item : items) {
            ids.add(fetchFriendId(item));
        }
        return ids;
    }

    private Function<WebDriver, List<WebElement>> allFriendsLoaded(By locator) {
        return wd -> {
            int previouslyLoaded = 0;
            List<WebElement> elements = wd.findElements(locator);
            while (previouslyLoaded < elements.size()) {
                previouslyLoaded = elements.size();
                scrollToBottomOfPage(wd);
                sleep(1000);
                elements = wd.findElements(locator);
            }
            return elements;
        };
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private void scrollToBottomOfPage(WebDriver wd) {
        if (!(wd instanceof JavascriptExecutor))
            throw new IllegalArgumentException(wd + " is not a JavascriptExecutor");
        scrollToBottomOfPage((JavascriptExecutor) wd);
    }

    private void scrollToBottomOfPage(JavascriptExecutor je) {
        je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private String fetchFriendId(WebElement item) {
        WebElement a = item.findElement(By.xpath(".//div[@class='fsl fwb fcb']/a"));
        String link = a.getAttribute("href");
        return usernameParser.parseFromLink(link);
    }

}
