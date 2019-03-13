package myapps;

import org.jsoup.Jsoup;
 
public class HTMLFormatter {
    public String html2text(String html) {
        html = html.replace("\\n", "");
        html = html.replace("\\t", "");
        html = html.replace("\\\"", "");
        return Jsoup.parse(html).text();
    }
}
