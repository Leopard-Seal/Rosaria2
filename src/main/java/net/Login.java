package net;

import global.Fast;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    public static void login(WebDriver driver){
        // ユーザーネームとパスワードの入力
        WebElement usernameField = driver.findElement(By.name("user[email]"));
        WebElement passwordField = driver.findElement(By.id("user_password"));
        usernameField.sendKeys(Fast.USERNAME);
        passwordField.sendKeys(Fast.PASSWORD);

        // ログインボタンをクリック
        WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
        loginButton.click();
    }
}
