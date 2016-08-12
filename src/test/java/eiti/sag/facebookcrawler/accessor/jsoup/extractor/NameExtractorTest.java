package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static eiti.sag.facebookcrawler.accessor.jsoup.extractor.Utils.getHtml;
import static org.assertj.core.api.Assertions.assertThat;

public class NameExtractorTest {

    NameExtractor nameExtractor = new NameExtractor();

    @Test
    public void shouldExtractName() throws Exception {
        Document document = Jsoup.parse(getHtml("/pages/zuck/about.html"));

        String name = nameExtractor.extract(document);

        assertThat(name).isEqualTo("Mark Zuckerberg");
    }

}