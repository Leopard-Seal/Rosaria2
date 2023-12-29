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
        System.setProperty("webdriver.chrome.driver", Fast.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.get(Fast.FIRST_VISIT_URL);
        WebElement element = driver.findElement(By.id("LoginLink"));
        element.click();

        Login.login(driver);
        for (String url : urls) {
            driver.get(Fast.BASE_URL + url);
            WebElement box = driver.findElement(By.className("common_left_box"));
            String htmlContent = box.getAttribute("outerHTML");
            String title = box.findElement(By.tagName("h1")).getText();

            Datasets.add(new Data(title, htmlContent, Fast.BASE_URL + url));
        }
    }
}
