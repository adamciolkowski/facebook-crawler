package eiti.sag.facebookcrawler.extractor;

import eiti.sag.facebookcrawler.model.FamilyMember;
import eiti.sag.facebookcrawler.model.Relationships;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RelationshipsExtractor extends PageletExtractor<Relationships> {

    @Override
    public Relationships extract(WebDriver wd) {
        WebElement pagelet = getPagelet(wd, "relationships");
        WebElement family = familyElement(pagelet);
        WebElement ulElement = findUlElement(family);
        Collection<FamilyMember> familyMembers = fetchFamilyMembers(ulElement);
        return new Relationships(familyMembers);
    }

    private WebElement familyElement(WebElement pagelet) {
        List<WebElement> e = pagelet.findElements(By.id("family-relationships-pagelet"));
        if(!e.isEmpty())
            return e.get(0);
        return pagelet.findElement(By.xpath(".//div[@data-pnref='family']"));
    }

    private Collection<FamilyMember> fetchFamilyMembers(WebElement ulElement) {
        List<WebElement> items = ulElement.findElements(By.xpath(".//li[@class='_43c8 _2ge8']"));
        List<FamilyMember> members = new ArrayList<>();
        for (WebElement item : items) {
            members.add(fetchFamilyMember(item));
        }
        return members;
    }

    private FamilyMember fetchFamilyMember(WebElement li) {
        WebElement span = li.findElement(By.xpath(".//span[@class='_50f5 _50f7']"));
        List<WebElement> a = span.findElements(By.tagName("a"));
        List<WebElement> divs = li.findElements(By.xpath(".//div[@class='fsm fwn fcg']"));
        WebElement div = divs.get(divs.size() - 1);
        if(a.isEmpty())
            return new FamilyMember(null, span.getText(), div.getText());
        return fetchFamilyMember(a.get(0), div);
    }

    private FamilyMember fetchFamilyMember(WebElement a, WebElement div) {
        String link = a.getAttribute("href");
        return new FamilyMember(link, a.getText(), div.getText());
    }

}
