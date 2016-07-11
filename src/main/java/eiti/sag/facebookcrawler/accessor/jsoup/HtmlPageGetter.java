package eiti.sag.facebookcrawler.accessor.jsoup;

import java.io.IOException;

public interface HtmlPageGetter {

    String get(String url) throws IOException;
}
