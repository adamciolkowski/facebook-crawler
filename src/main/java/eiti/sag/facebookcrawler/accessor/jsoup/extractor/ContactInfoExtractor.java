package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.BasicInfo;
import eiti.sag.facebookcrawler.model.ContactInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ContactInfoExtractor implements DocumentExtractor<ContactInfo> {

    @Override
    public ContactInfo extract(Document document) {
        return new ContactInfo(extractBasicInfo(document));
    }

    private BasicInfo extractBasicInfo(Document document) {
        return new BasicInfo(extractBirthday(document), extractGender(document));
    }

    private String extractBirthday(Document document) {
        return extractEntry(document, "_4vs2");
    }

    private String extractGender(Document document) {
        return extractEntry(document, "_3ms8");
    }

    private String extractEntry(Element element, String entryClass) {
        String cssQuery = "div.hidden_elem ul.uiList.fbProfileEditExperiences._4kg._4ks " +
                "li._3pw9._2pi4._2ge8." + entryClass + " div.clearfix div._4bl7._pt5 div.clearfix div span._50f4";
        return extractText(element, cssQuery);
    }

    private String extractText(Element element, String cssQuery) {
        Elements e = element.select(cssQuery);
        if(e.isEmpty())
            return null;
        return e.get(0).text();
    }
}
