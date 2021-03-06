package eiti.sag.facebookcrawler.accessor.jsoup;

import eiti.sag.facebookcrawler.accessor.jsoup.extractor.ContentNotAvailableException;
import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.accessor.FetchUserFailedException;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.ContactInfoExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.ExperienceExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.FriendsIdsExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.NameExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.PlacesExtractor;
import eiti.sag.facebookcrawler.accessor.jsoup.extractor.RelationshipsExtractor;
import eiti.sag.facebookcrawler.accessor.util.UsernameParser;
import eiti.sag.facebookcrawler.model.FacebookUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;

public class JsoupFacebookAccessor implements FacebookAccessor {

    private static final String BASE_URL = "https://www.facebook.com/";

    private final HtmlPageGetter htmlPageGetter;

    private final UsernameParser usernameParser = new UsernameParser(BASE_URL);

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
        } catch (ContentNotAvailableException e) {
            throw new FetchUserFailedException(e);
        }
    }

    private FacebookUser doFetchUser(String username) throws IOException {
        FacebookUser user = new FacebookUser();
        user.setUsername(username);
        user.setName(new NameExtractor().extract(getPage(username, "about")));
        user.setExperience(new ExperienceExtractor().extract(getSectionPage(username, "education")));
        user.setPlaces(new PlacesExtractor().extract(getSectionPage(username, "living")));
        user.setContactInfo(new ContactInfoExtractor().extract(getSectionPage(username, "contact-info")));
        user.setRelationships(new RelationshipsExtractor(usernameParser).extract(getSectionPage(username, "relationship")));
        user.setFriendsIds(new FriendsIdsExtractor(usernameParser).extract(getPage(username, "friends_all")));
        return user;
    }

    private Document getSectionPage(String username, String section) throws IOException {
        return getPage(username, "about?section=" + section);
    }

    private Document getPage(String username, String relativeUrl) throws IOException {
        return Jsoup.parse(htmlPageGetter.get(BASE_URL + username + "/" + relativeUrl));
    }

}
