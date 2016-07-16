package eiti.sag.facebookcrawler.accessor.jsoup;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.Education;
import eiti.sag.facebookcrawler.model.Experience;
import eiti.sag.facebookcrawler.model.FacebookUser;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        this(new JsoupHtmlGetter(cookies.asMap(), Locale.US));
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
        Document aboutPage = getPage(username, "about");
        Document educationPage = getPage(username, "about?section=education");
        Document friendsPage = getPage(username, "friends_all");
        return fetchUser(aboutPage, educationPage, friendsPage);
    }

    private Document getPage(String username, String section) throws IOException {
        return Jsoup.parse(htmlPageGetter.get(BASE_URL + username + "/" + section));
    }

    private FacebookUser fetchUser(Document aboutPage, Document educationPage, Document friendsPage) {
        FacebookUser user = new FacebookUser();
        user.setName(extractName(aboutPage));
        user.setFriendsIds(fetchFriendsIds(friendsPage));
        user.setExperience(extractExperience(educationPage));
        return user;
    }

    private String extractName(Document document) {
        return document.getElementById("fb-timeline-cover-name").text();
    }

    private Experience extractExperience(Document educationPage) {
        List<Education> works = extractEducation(educationPage);
        return new Experience(null, works);
    }

    private List<Education> extractEducation(Document educationPage) {
        String cssQuery = "div[data-pnref='edu'] " +
                "ul[class='uiList fbProfileEditExperiences _4kg _4ks'] > li";
        Elements elements = educationPage.select(cssQuery);
        return extractEducation(elements);
    }

    private List<Education> extractEducation(Elements elements) {
        return elements.stream()
                .map(this::extractEducation)
                .collect(toList());
    }

    private Education extractEducation(Element element) {
        Element a = element.select("div[class='_2lzr _50f5 _50f7'] a").get(0);
        String name = a.text();
        String link = a.attr("href");
        return new Education(name, extractInfo(element), link);
    }

    private String extractInfo(Element e) {
        Elements elements = e.select("div[class='fsm fwn fcg']");
        if(elements.isEmpty())
            return null;
        return elements.get(0).text();
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
