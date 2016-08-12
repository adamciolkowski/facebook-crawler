package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.Places;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static eiti.sag.facebookcrawler.accessor.jsoup.extractor.Utils.getHtml;
import static org.assertj.core.api.Assertions.assertThat;

public class PlacesExtractorTest {

    PlacesExtractor placesExtractor = new PlacesExtractor();

    @Test
    public void shouldExtractPlaces() throws Exception {
        Document document = Jsoup.parse(getHtml("/pages/zuck/living.html"));

        Places places = placesExtractor.extract(document);

        assertThat(places.getCurrentCity().getName()).isEqualTo("Palo Alto, California");
        assertThat(places.getHometown().getName()).isEqualTo("Dobbs Ferry, New York");
    }
}