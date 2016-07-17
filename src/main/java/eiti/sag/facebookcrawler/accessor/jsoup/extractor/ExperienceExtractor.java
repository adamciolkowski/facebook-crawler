package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.Education;
import eiti.sag.facebookcrawler.model.Experience;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ExperienceExtractor implements DocumentExtractor<Experience> {

    @Override
    public Experience extract(Document document) {
        List<Education> education = extractEducation(document);
        return new Experience(null, education);
    }

    private List<Education> extractEducation(Document page) {
        String cssQuery = "div[data-pnref='edu'] " +
                "ul[class='uiList fbProfileEditExperiences _4kg _4ks'] > li";
        return extractEducation(page.select(cssQuery));
    }

    private List<Education> extractEducation(Elements elements) {
        return elements.stream()
                .map(this::extractEducation)
                .collect(toList());
    }

    private Education extractEducation(Element element) {
        Element a = element.select("div[class='_2lzr _50f5 _50f7'] a").get(0);
        String name = a.text();
        String link = a.attr("href");
        return new Education(name, extractInfo(element), link);
    }

    private String extractInfo(Element e) {
        Elements elements = e.select("div[class='fsm fwn fcg']");
        if(elements.isEmpty())
            return null;
        return elements.get(0).text();
    }

}
