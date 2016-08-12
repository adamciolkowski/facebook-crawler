package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.accessor.util.UsernameParser;
import org.jsoup.nodes.Document;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FriendsIdsExtractor implements DocumentExtractor<List<String>> {

    private final UsernameParser usernameParser;

    public FriendsIdsExtractor(UsernameParser usernameParser) {
        this.usernameParser = usernameParser;
    }

    @Override
    public List<String> extract(Document document) {
        String cssQuery = "ul[data-pnref='friends'] div[class='fsl fwb fcb'] a";
        return document.select(cssQuery).stream()
                .map(e -> e.attr("href"))
                .map(usernameParser::parseFromLink)
                .collect(toList());
    }

}
