package main;

import global.Datasets;
import io.Save;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Rosaria rosaria = new Rosaria();
        int index = 2;
        rosaria.run(index);
        System.out.println(Rosaria.urlList.size());
        Save.saveToFile(Rosaria.urlList , "./data/urllist"+(index-1)+".txt");
        WebCrawler.run(Rosaria.urlList);
        Datasets.saveJson("./data/data-"+(index-1)+".json");
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }
}
