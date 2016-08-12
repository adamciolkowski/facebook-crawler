package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import eiti.sag.facebookcrawler.model.Education;
import eiti.sag.facebookcrawler.model.Experience;
import eiti.sag.facebookcrawler.model.Work;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static eiti.sag.facebookcrawler.accessor.jsoup.extractor.Utils.getHtml;
import static org.assertj.core.api.Assertions.assertThat;

public class ExperienceExtractorTest {

    ExperienceExtractor experienceExtractor = new ExperienceExtractor();

    @Test
    public void shouldExtractExperience() throws Exception {
        Document document = Jsoup.parse(getHtml("/pages/zuck/education.html"));

        Experience experience = experienceExtractor.extract(document);

        assertThat(experience.getEducation()).hasSize(3);
        Education education = experience.getEducation().iterator().next();
        assertThat(education.getPlace()).isEqualTo("Harvard University");
        assertThat(education.getLink()).isEqualTo("https://www.facebook.com/Harvard/");

        assertThat(experience.getWorks()).hasSize(2);
        Work work = experience.getWorks().iterator().next();
        assertThat(work.getWorkingPlace()).isEqualTo("Chan Zuckerberg Initiative");
        assertThat(work.getLink()).isEqualTo("https://www.facebook.com/chanzuckerberginitiative/");
    }
}