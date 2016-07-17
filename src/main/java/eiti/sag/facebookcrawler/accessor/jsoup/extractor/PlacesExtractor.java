package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.Location;
import eiti.sag.facebookcrawler.model.Places;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PlacesExtractor implements DocumentExtractor<Places> {

    @Override
    public Places extract(Document document) {
        Location currentCity = extractLocation(document, "current_city");
        Location hometown = extractLocation(document, "hometown");
        return new Places(currentCity, hometown);
    }

    private Location extractLocation(Document document, String id) {
        Element e = document.getElementById(id);
        if(e == null)
            return null;
        return extractLocation(e);
    }

    private Location extractLocation(Element e) {
        Element a = e.select("a").get(0);
        String name = a.text();
        String link = a.attr("href");
        return new Location(name, link);
    }

}
