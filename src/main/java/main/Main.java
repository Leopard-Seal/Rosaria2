package main;

import global.Datasets;
import io.Save;

public class Main {
    public static void main(String[] args) {
        Rosaria rosaria = new Rosaria();
        rosaria.run();
        System.out.println(Rosaria.urlList.size());
        Save.saveToFile(Rosaria.urlList , "./data/urllist.txt");
        for (String url : Rosaria.urlList) {
            System.out.println(url);
        }
        WebCrawler.run(Rosaria.urlList);
        Datasets.saveJson("./data/data.json");
    }
}
