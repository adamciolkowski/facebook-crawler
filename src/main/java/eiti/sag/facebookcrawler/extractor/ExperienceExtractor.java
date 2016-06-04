package eiti.sag.facebookcrawler.extractor;

import eiti.sag.facebookcrawler.model.Education;
import eiti.sag.facebookcrawler.model.Experience;
import eiti.sag.facebookcrawler.model.Work;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ExperienceExtractor extends PageletExtractor<Experience> {

    public Experience extract(WebDriver wd) {
        WebElement pagelet = getPagelet(wd, "eduwork");
        List<Work> works = extractWorks(groupElement(pagelet, "work"));
        List<Education> education = extractEducationList(groupElement(pagelet, "edu"));
        return new Experience(works, education);
    }

    private WebElement groupElement(WebElement pagelet, String ref) {
        WebElement e = pagelet.findElement(By.xpath(".//div[@data-pnref='" + ref + "']"));
        return findUlElement(e);
    }

    private List<Work> extractWorks(WebElement ulElement) {
        String xpath = ".//li[@class='_43c8 _5f6p fbEditProfileViewExperience experience']";
        List<WebElement> items = ulElement.findElements(By.xpath(xpath));
        List<Work> works = new ArrayList<>();
        for (WebElement item : items) {
            works.add(extractWork(item));
        }
        return works;
    }

    private Work extractWork(WebElement li) {
        WebElement div = li.findElement(By.xpath(".//div[@class='_2lzr _50f5 _50f7']"));
        WebElement a = div.findElement(By.tagName("a"));
        String name = a.getText();
        String info = extractInfo(li);
        String link = a.getAttribute("href");
        return new Work(name, info, link);
    }

    private List<Education> extractEducationList(WebElement ulElement) {
        String xpath = ".//li[@class='_43c8 _5f6p fbEditProfileViewExperience experience']";
        List<WebElement> items = ulElement.findElements(By.xpath(xpath));
        List<Education> works = new ArrayList<>();
        for (WebElement item : items) {
            works.add(extractEducation(item));
        }
        return works;
    }

    private Education extractEducation(WebElement li) {
        WebElement div = li.findElement(By.xpath(".//div[@class='_2lzr _50f5 _50f7']"));
        WebElement a = div.findElement(By.tagName("a"));
        String name = a.getText();
        String info = extractInfo(li);
        String link = a.getAttribute("href");
        return new Education(name, info, link);
    }

}
