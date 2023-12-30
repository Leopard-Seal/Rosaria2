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

    public static void run(List<String> urls, int max) {
        System.setProperty("webdriver.chrome.driver", Fast.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1250, 500));
        driver.get(Fast.FIRST_VISIT_URL);
        WebElement element = driver.findElement(By.id("LoginLink"));
        element.click();

        Login.login(driver);
        int i = 0;
        for (String url : urls) {
            try{
                driver.get(Fast.BASE_URL + url);
                WebElement box = driver.findElement(By.className("common_left_box"));
                String htmlContent = box.getAttribute("outerHTML");
                String title = box.findElement(By.tagName("h1")).getText();

                Datasets.add(new Data(title, htmlContent, Fast.BASE_URL + url));
            }catch (Exception ignored){}
            System.out.println(i + " / " + urls.size() + " : " + url);
            i++;
            if (i > max) break;
        }
        driver.close();
    }
}
