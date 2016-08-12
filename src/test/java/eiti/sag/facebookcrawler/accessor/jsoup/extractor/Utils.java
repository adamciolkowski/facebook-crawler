package eiti.sag.facebookcrawler.accessor.jsoup.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Utils {

    public static String getHtml(String path) throws IOException {
        return readString(Utils.class.getResourceAsStream(path));
    }

    private static String readString(InputStream in) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[8192];
        int read;
        while ((read = reader.read(buffer)) != -1) {
            sb.append(buffer, 0, read);
        }
        return sb.toString();
    }
}
