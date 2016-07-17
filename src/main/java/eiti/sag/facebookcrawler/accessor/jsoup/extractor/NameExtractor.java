package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import org.jsoup.nodes.Document;

public class NameExtractor implements DocumentExtractor<String> {

    @Override
    public String extract(Document document) {
        return document.getElementById("fb-timeline-cover-name").text();
    }

}
