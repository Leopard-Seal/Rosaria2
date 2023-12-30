package global;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.html.HTMLAnalyzer;

public class Data {
    private final Document document;
    public String title;
    public String text;
    public String url;
    public List<String> tag = new ArrayList<>();
    public Map<String, String> qAndA;

    public Data(String title, String htmlcontent, String url) {
        this.document = Jsoup.parse(htmlcontent);
        this.text = mainText();
        this.qAndA = HTMLAnalyzer.extractQuestionsAndAnswers(this.document);
        setTag();
        this.title = title;
        this.url = url;
    }

    public String mainText(){
        Element e =  document.getElementById("dldetail_body");
        if (e == null) return "null";
        return e.text();
    }

    public void setTag(){
        Elements elements =  document.getElementsByClass("tag");
        for (Element element : elements) {
            tag.add(element.text());
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
