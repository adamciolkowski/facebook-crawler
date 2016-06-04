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
        WebElement family = pagelet.findElement(By.id("family-relationships-pagelet"));
        WebElement ulElement = findUlElement(family);
        Collection<FamilyMember> familyMembers = fetchFamilyMembers(ulElement);
        return new Relationships(familyMembers);
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
        WebElement div = li.findElement(By.xpath(".//span[@class='_50f5 _50f7']"));
        WebElement a = div.findElement(By.tagName("a"));
        String name = a.getText();
        String info = extractInfo(li);
        String link = a.getAttribute("href");
        return new FamilyMember(link, name, info);
    }

}
