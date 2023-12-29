package main;

import global.Fast;
import net.OpenHTML;
import net.html.HTMLAnalyzer;
import util.Security;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Rosaria {
    public static List<String> companies = new CopyOnWriteArrayList<>();
    public static List<String> essearch = new CopyOnWriteArrayList<>();
    public static List<String> sheets = new CopyOnWriteArrayList<>();

    public static int THREADS = Runtime.getRuntime().availableProcessors() * 2;

    public void run() {
        System.out.println(THREADS);
        int index = 2;
        int max = 37;
        String url0 = Fast.FIRST_VISIT_URL + index;

        //URLが存在するかどうか
        if(!Security.isNotFound(url0)){
            // まず、url0からリンクを会社のリンクを抜き出す
            Document doc0 = new OpenHTML(url0).getDocument();
            List<String> hrefs = HTMLAnalyzer.extractLinksFromClassName(doc0, "relation_companies");
            companies.addAll(hrefs);

            // スレッドプールの設定
            try (ExecutorService executor = Executors.newFixedThreadPool(THREADS)) {
                // 各リンクに対して非同期処理を行う
                for (String companyLink : companies) {
                    executor.submit(() -> process1(companyLink));
                }

                // エグゼキュータのシャットダウン処理
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                }
            }

            try (ExecutorService executor = Executors.newFixedThreadPool(THREADS)){
                //シートのリンクから同じようにhrefを抜き出す。
                for (String sheetLink : essearch) {
                    executor.submit(() -> process2(sheetLink));
                }

                executor.shutdown();
                try {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                }
            }

            System.out.println("sheet is" + sheets);
        }
    }

    public static void process1(String url) {
        OpenHTML openHTML = new OpenHTML(Fast.BASE_URL + url);
        Document doc = openHTML.getDocument();
        List<String> links = HTMLAnalyzer.extractLinksFromClassName(doc, "essearch_common_wrap");
        links.remove(links.size() - 1);
        links.remove(0);

        essearch.addAll(links);
    }

    public static void process2(String url) {
        OpenHTML openHTML = new OpenHTML(Fast.BASE_URL + url);
        Document doc = openHTML.getDocument();
        List<String> links = HTMLAnalyzer.extractLinksFromClassName(doc, "company_sheets");
        sheets.addAll(links);
    }
}
