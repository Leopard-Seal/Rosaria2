package main;

import global.Data;
import global.Datasets;
import global.Fast;
import net.Login;
import org.openqa.selenium.By;
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
            }catch (Exception e){
                Datasets.saveJson("./data/data" + System.currentTimeMillis()  + ".json");
            }
            System.out.println(i + " : "+url);
            i++;
            if (i > max) break;
        }
        driver.close();
    }

    public static void main(String[] args) {
        List<String> urls = List.of("/entry_sheets/40006","/entry_sheets/38547");
        System.setProperty("webdriver.chrome.driver", Fast.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
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
            }catch (Exception e){
                Datasets.saveJson("./data/data" + System.currentTimeMillis()  + ".json");
            }
            System.out.println(i + " : "+url);
            i++;
        }
        driver.close();
        Datasets.saveJson("./data/testdata.json");
    }



}
