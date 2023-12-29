package net.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HTMLAnalyzer {

    public static List<String> extractLinksFromClassName(Document doc, String className) {
        List<String> links = new ArrayList<>();
        Elements elementsWithClass = doc.getElementsByClass(className);

        for (Element element : elementsWithClass) {
            Elements linkElements = element.select("a[href]");
            for (Element link : linkElements) {
                links.add(link.attr("href"));
            }
        }

        return links;
    }
}
