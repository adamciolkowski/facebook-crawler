package eiti.sag.facebookcrawler.accessor.jsoup;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public class JsoupFacebookAccessor implements FacebookAccessor {

    private static final String BASE_URL = "https://www.facebook.com/";

    private final HtmlPageGetter htmlPageGetter;

    public JsoupFacebookAccessor(FacebookAuthCookies cookies) {
        this(new JsoupHtmlGetter(cookies.asMap(), Locale.getDefault()));
    }

    public JsoupFacebookAccessor(HtmlPageGetter htmlPageGetter) {
        this.htmlPageGetter = htmlPageGetter;
    }

    @Override
    public FacebookUser fetchUser(String username) {
        try {
            return doFetchUser(username);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private FacebookUser doFetchUser(String username) throws IOException {
        String html = htmlPageGetter.get(BASE_URL + username + "/friends_all");
        Document parsed = Jsoup.parse(html);
        return doFetchUser(parsed);
    }

    private FacebookUser doFetchUser(Document document) {
        FacebookUser user = new FacebookUser();
        user.setFriendsIds(fetchFriendsIds(document));
        return user;
    }

    private List<String> fetchFriendsIds(Document document) {
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
                .orElse(link.substring(BASE_URL.length(), link.indexOf('?')));
    }

    @Override
    public void logout() { }

}
