package eiti.sag.facebookcrawler.accessor.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.util.List;

public class UsernameParser {

    private final String baseUrl;

    public UsernameParser(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String parseFromLink(String link) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(URI.create(link), "UTF-8");
        return pairs.stream()
                .filter(nv -> nv.getName().equals("id"))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(link.substring(baseUrl.length(), endIndex(link)));
    }

    private int endIndex(String link) {
        int i = link.indexOf('?');
        return i > 0 ? i : link.length();
    }

}
