package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.BasicInfo;
import eiti.sag.facebookcrawler.model.ContactInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static eiti.sag.facebookcrawler.accessor.jsoup.extractor.Utils.getHtml;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactInfoExtractorTest {

    ContactInfoExtractor contactInfoExtractor = new ContactInfoExtractor();

    @Test
    public void shouldExtractContactInfo() throws Exception {
        Document document = Jsoup.parse(getHtml("/pages/zuck/contact-info.html"));

        ContactInfo contactInfo = contactInfoExtractor.extract(document);

        BasicInfo basicInfo = contactInfo.getBasicInfo();
        assertThat(basicInfo.getGender()).isEqualTo("Male");
        assertThat(basicInfo.getBirthday()).isEqualTo("May 14, 1984");
        assertThat(basicInfo.getLanguages()).containsOnly("English", "Mandarin Chinese");
    }

}