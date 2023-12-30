package net.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, String> extractQuestionsAndAnswers(Document document) {
        Map<String, String> qAndA = new HashMap<>();
        Elements questions = document.select(".esdetail_main h3.esdetail_subtitle");

        for (Element question : questions) {
            String questionText = question.text();
            Element answer = question.nextElementSibling().nextElementSibling();
            String answerText = answer.text();

            qAndA.put(questionText, answerText);
        }

        return qAndA;
    }
}
