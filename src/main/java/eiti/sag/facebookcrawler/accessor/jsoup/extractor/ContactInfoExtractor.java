package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.BasicInfo;
import eiti.sag.facebookcrawler.model.ContactInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ContactInfoExtractor implements DocumentExtractor<ContactInfo> {

    @Override
    public ContactInfo extract(Document document) {
        return new ContactInfo(extractBasicInfo(document));
    }

    private BasicInfo extractBasicInfo(Document document) {
        return new BasicInfo(extractBirthday(document), extractGender(document), extractLanguages(document));
    }

    private String extractBirthday(Document document) {
        return extractEntry(document, "_4vs2");
    }

    private String extractGender(Document document) {
        return extractEntry(document, "_3ms8");
    }

    private List<String> extractLanguages(Document document) {
        String cssQuery = "div.hidden_elem ul.uiList.fbProfileEditExperiences._4kg._4ks " +
                "li._3pw9._2pi4._2ge8 div.clearfix div._4bl7._pt5 div.clearfix " +
                "div.fsm.fwn.fcg span._50f4";
        return document.select(cssQuery).stream()
                .map(Element::text)
                .collect(toList());
    }

    private String extractEntry(Element element, String entryClass) {
        String cssQuery = "div.hidden_elem ul.uiList.fbProfileEditExperiences._4kg._4ks " +
                "li._3pw9._2pi4._2ge8." + entryClass + " div.clearfix div._4bl7._pt5 div.clearfix div span._50f4";
        return extractText(element, cssQuery);
    }

    private String extractText(Element element, String cssQuery) {
        Elements e = element.select(cssQuery);
        if(dataFieldAbsentOrEmpty(e))
            return null;
        return e.get(0).text();
    }

    private boolean dataFieldAbsentOrEmpty(Elements e) {
        return e.isEmpty() || !e.get(0).children().isEmpty();
    }
}
