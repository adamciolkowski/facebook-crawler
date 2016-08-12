package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.FamilyMember;
import eiti.sag.facebookcrawler.model.Relationships;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class RelationshipsExtractor implements DocumentExtractor<Relationships> {

    @Override
    public Relationships extract(Document document) {
        return new Relationships(extractFamilyMembers(document));
    }

    private Collection<FamilyMember> extractFamilyMembers(Document document) {
        String cssQuery = "div.hidden_elem ul.uiList.fbProfileEditExperiences._4kg._4ks " +
                "li._43c8._2ge8 div.clearfix div._4bl9 div.clearfix " +
                "div._42ef div._6a div._6a._6b";
        Elements select = document.select(cssQuery);
        return select.stream()
                .filter(e -> !e.text().isEmpty())
                .map(this::extractFamilyMember)
                .collect(toList());
    }

    private FamilyMember extractFamilyMember(Element e) {
        String link = e.select("a").attr("href");
        Elements div = e.select("div.fsm.fwn.fcg");
        String name = div.get(0).text();
        String type = div.get(1).text();
        return new FamilyMember(link, name, type);
    }
}
