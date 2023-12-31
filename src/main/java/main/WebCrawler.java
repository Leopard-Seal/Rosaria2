package main;

import global.Data;
import global.Datasets;
import global.Fast;
import net.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class WebCrawler {

    public static void run(List<String> urls) {
        run(urls, Integer.MAX_VALUE);
    }

    static int url_size = 0;
    final static int c = 100;

    public static void run(List<String> urls, int max) {

        url_size = urls.size();
        System.setProperty("webdriver.chrome.driver", Fast.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1250, 500));
        driver.get(Fast.FIRST_VISIT_URL);
        WebElement element = driver.findElement(By.id("LoginLink"));
        element.click();

        Login.login(driver);
        long start = 0;
        int i = 0;
        for (String url : urls) {
            if (i % c == 0 || (url_size - 1) == i) start = System.currentTimeMillis();
            try{
                driver.get(Fast.BASE_URL + url);
                WebElement box = driver.findElement(By.className("common_left_box"));
                String htmlContent = box.getAttribute("outerHTML");
                String title = box.findElement(By.tagName("h1")).getText();

                Datasets.add(new Data(title, htmlContent, Fast.BASE_URL + url));
            }catch (Exception ignored){}

            if (i % c == 0 || (url_size - 1) == i) calcElapsedTime(start, i);
            i++;
            if (i > max) break;
        }
        driver.close();
    }

    public static void calcElapsedTime(long startTime, int index) {
        long end = System.currentTimeMillis();
        long time = end - startTime;

        System.out.println(formatTime(time) + " / 1 page , progress : " + index + " / " + url_size);

        long total = time * (url_size - index);
        System.out.println("Estimated processing time ? : " + formatTime(total));

    }

    public static String formatTime(long millis) {
        long hours = millis / (3600 * 1000);
        long minutes = (millis % (3600 * 1000)) / (60 * 1000);
        long seconds = (millis % (60 * 1000)) / 1000;
        long milliseconds = millis % 1000;

        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
    }

}
