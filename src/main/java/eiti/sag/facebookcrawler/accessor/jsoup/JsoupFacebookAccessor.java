package eiti.sag.facebookcrawler.accessor.jsoup;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class JsoupFacebookAccessor implements FacebookAccessor {

    private static final String BASE_URL = "https://www.facebook.com/";

    private final FacebookAuthCookies cookies;

    private final Locale locale;

    public JsoupFacebookAccessor(FacebookAuthCookies cookies) {
        this(cookies, Locale.getDefault());
    }

    public JsoupFacebookAccessor(FacebookAuthCookies cookies, Locale locale) {
        this.cookies = cookies;
        this.locale = locale;
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
        Connection connection = prepareConnection(username);
        Document document = connection.get();
        String extracted = prepareDocument(document);
        Document parsed = Jsoup.parse(extracted);
        return doFetchUser(parsed);
    }

    private Connection prepareConnection(String username) {
        return Jsoup.connect(BASE_URL + username + "/friends_all")
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/50.0.2661.102 Chrome/50.0.2661.102 Safari/537.36")
                .cookies(cookies.asMap())
                .cookie("locale", locale.toString());
    }

    private String prepareDocument(Document document) {
        return document.body().children().stream()
                .filter(e -> !isScriptTag(e))
                .map(Element::toString)
                .map(this::removeHtmlComments)
                .collect(joining());
    }

    private boolean isScriptTag(Element e) {
        return e.tagName().equals("script");
    }

    private String removeHtmlComments(String text) {
        return text.replaceAll("<!-- | -->", "");
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
