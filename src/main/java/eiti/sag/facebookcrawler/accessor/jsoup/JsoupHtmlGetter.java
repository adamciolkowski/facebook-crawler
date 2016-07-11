package eiti.sag.facebookcrawler.accessor.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.joining;

class JsoupHtmlGetter implements HtmlPageGetter {

    private final Map<String, String> cookies;
    private final Locale locale;

    JsoupHtmlGetter(Map<String, String> cookies, Locale locale) {
        this.cookies = cookies;
        this.locale = locale;
    }

    @Override
    public String get(String url) throws IOException {
        Connection connection = prepareConnection(url);
        Document document = connection.get();
        return prepareDocument(document);
    }

    private Connection prepareConnection(String url) {
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/50.0.2661.102 Chrome/50.0.2661.102 Safari/537.36")
                .cookies(cookies)
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
}
