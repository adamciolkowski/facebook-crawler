package eiti.sag.facebookcrawler.accessor.jsoup;

import java.util.HashMap;
import java.util.Map;

public class FacebookAuthCookies {

    private final String cUser;
    private final String datr;
    private final String xs;

    public FacebookAuthCookies(String cUser, String datr, String xs) {
        this.cUser = cUser;
        this.datr = datr;
        this.xs = xs;
    }

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        map.put("c_user", cUser);
        map.put("datr", datr);
        map.put("xs", xs);
        return map;
    }
}
