package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NameExtractor implements DocumentExtractor<String> {

    @Override
    public String extract(Document document) {
        Element e = document.getElementById("fb-timeline-cover-name");
        if(e == null)
            throw new ContentNotAvailableException("Account does not exist or you are blocked from viewing it");
        return e.text();
    }

}
