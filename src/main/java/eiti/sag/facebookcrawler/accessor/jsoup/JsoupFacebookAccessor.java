package eiti.sag.facebookcrawler.accessor.jsoup;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.ContactInfoExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.ExperienceExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.FriendsIdsExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.NameExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.PlacesExtractor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;

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
        FacebookUser user = new FacebookUser();
        user.setUsername(username);
        user.setName(new NameExtractor().extract(getPage(username, "about")));
        user.setExperience(new ExperienceExtractor().extract(getPage(username, "about?section=education")));
        user.setPlaces(new PlacesExtractor().extract(getPage(username, "about?section=living")));
        user.setContactInfo(new ContactInfoExtractor().extract(getPage(username, "about?section=contact-info")));
        user.setFriendsIds(new FriendsIdsExtractor(BASE_URL).extract(getPage(username, "friends_all")));
        return user;
    }

    private Document getPage(String username, String section) throws IOException {
        return Jsoup.parse(htmlPageGetter.get(BASE_URL + username + "/" + section));
    }

    @Override
    public void logout() { }

}
