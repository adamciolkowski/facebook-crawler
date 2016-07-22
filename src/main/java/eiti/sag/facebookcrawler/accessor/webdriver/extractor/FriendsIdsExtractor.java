package eiti.sag.facebookcrawler.accessor.webdriver.extractor;

import com.google.common.base.Function;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsIdsExtractor extends PageletExtractor<Collection<String>> {

    private final String baseUrl;

    public FriendsIdsExtractor(String baseUrl) {
        this.baseUrl = baseUrl;
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
        return extractUserId(link);
    }

    private String extractUserId(String link) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(URI.create(link), "UTF-8");
        Map<String, String> map = toMap(pairs);
        if (map.containsKey("id"))
            return map.get("id");
        return link.substring(baseUrl.length(), link.indexOf('?'));
    }

    private Map<String, String> toMap(List<NameValuePair> pairs) {
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs)
            map.put(pair.getName(), pair.getValue());
        return map;
    }
}
