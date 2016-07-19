package eiti.sag.facebookcrawler.extractor;

import eiti.sag.facebookcrawler.model.BasicInfo;
import eiti.sag.facebookcrawler.model.ContactInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactExtractor extends PageletExtractor<ContactInfo> {

    @Override
    public ContactInfo extract(WebDriver wd) {
        WebElement pagelet = getPagelet(wd, "basic");
        WebElement ulElement = findUlElement(pagelet);
        BasicInfo basicInfo = extractBasicInfo(ulElement);
        return new ContactInfo(basicInfo);
    }

    private BasicInfo extractBasicInfo(WebElement ulElement) {
        String birthday = extractBirthday(ulElement);
        String gender = extractGender(ulElement);
        return new BasicInfo(birthday, gender);
    }

    private String extractBirthday(WebElement ulElement) {
        List<WebElement> li = ulElement.findElements(By.xpath(".//li[@data-privacy-fbid='8787510733']"));
        if(li.isEmpty())
            return null;
        WebElement span = getSpanWithValue(li.get(0));
        if(!isBirthdayFieldFilled(span))
            return null;
        return span.getText();
    }

    private boolean isBirthdayFieldFilled(WebElement span) {
        return span.findElements(By.xpath(".//div[@data-field-type='339581476191384']")).isEmpty();
    }

    private String extractGender(WebElement ulElement) {
        List<WebElement> li = ulElement.findElements(By.xpath(".//li[@class='_3pw9 _2pi4 _2ge8 _3ms8']"));
        if(li.isEmpty())
            return null;
        WebElement span = getSpanWithValue(li.get(0));
        return span.getText();
    }

    private WebElement getSpanWithValue(WebElement li) {
        return li.findElement(By.xpath(".//span[@class='_50f4']"));
    }
}
