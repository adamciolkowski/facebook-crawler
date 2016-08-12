package eiti.sag.facebookcrawler.accessor.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsernameParserTest {

    String baseUrl = "https://www.facebook.com/";

    UsernameParser usernameParser = new UsernameParser(baseUrl);

    @Test
    public void shouldParseUsername() {
        String link = "https://www.facebook.com/zuck?fref=pb&hc_location=friends_tab";

        String username = usernameParser.parseFromLink(link);

        assertThat(username).isEqualTo("zuck");
    }

    @Test
    public void shouldParseUsernameContainedInIdUrlParameter() {
        String link = "https://www.facebook.com/profile.php?id=100001234567890&fref=pb&hc_location=friends_tab";

        String username = usernameParser.parseFromLink(link);

        assertThat(username).isEqualTo("100001234567890");
    }

}