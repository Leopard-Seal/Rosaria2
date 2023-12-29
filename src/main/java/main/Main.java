package main;

import global.Datasets;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Rosaria rosaria = new Rosaria();
        rosaria.run();
        List<String> urlList = new ArrayList<>();
        for (int i = 0 ; i < 12; i++){
            urlList.add(Rosaria.urlList.get(i));
        }
        System.out.println("List : " + urlList.size());
        WebCrawler.run(urlList);
        Datasets.saveJson("./data/data.json");
    }
}
