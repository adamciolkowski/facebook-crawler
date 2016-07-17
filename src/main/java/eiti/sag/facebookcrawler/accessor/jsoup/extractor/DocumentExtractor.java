package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import org.jsoup.nodes.Document;

public interface DocumentExtractor<T> {

    T extract(Document document);
}
