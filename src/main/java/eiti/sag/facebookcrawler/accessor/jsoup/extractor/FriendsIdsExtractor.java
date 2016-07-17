package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FriendsIdsExtractor implements DocumentExtractor<List<String>> {

    private final String baseUrl;

    public FriendsIdsExtractor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<String> extract(Document document) {
        String cssQuery = "ul[data-pnref='friends'] div[class='fsl fwb fcb'] a";
        return document.select(cssQuery).stream()
                .map(e -> e.attr("href"))
                .map(this::extractUserId)
                .collect(toList());
    }

    private String extractUserId(String link) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(URI.create(link), "UTF-8");
        return pairs.stream()
                .filter(nv -> nv.getName().equals("id"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(link.substring(baseUrl.length(), link.indexOf('?')));
    }
}
