package hw_tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;

public class hw_ex3 {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "AndroidDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "Users/alekseyverin/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown(){
        driver.quit();
    }

    /** Ex3: Тест: отмена поиска
     * Написать тест, который:
     * Ищет какое-то слово
     * Убеждается, что найдено несколько статей
     * Отменяет поиск
     * Убеждается, что результат поиска пропал
     */


    @Test
    public void testCancelResultOfSearchSeveralArticles(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find search Wikipedia input",
                5);

        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");


        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Appium","Can't find search input",
                5);

        CheckForSeveralArticlesAreFound("Appium");

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5);

        checkEmptySearchField("Appium");

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return  element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return  element;
    }

    private void checksForTextInWebElement (By by, String typeAtribute, String text){
        WebElement element = waitForElementPresent(by,"Web element is not present",5);
        Assert.assertEquals(
                "Error! text [" + text + "] is not present.",
                String.valueOf(element.getAttribute(typeAtribute)),
                text
        );
    }

    private void CheckForSeveralArticlesAreFound(String word){
        waitForElementPresent(By.xpath("//*[contains(@text,'" + word + "')]"),
                "Web element is not present",15);
        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@text,'" + word + "')]"));
        Assert.assertTrue("Articles less than 2",elementsList.size() > 1);
    }

    private void checkEmptySearchField(String word){
        checksForTextInWebElement(By.id("org.wikipedia:id/search_src_text"),"text","Search…");

        List<WebElement> elementsList = driver.findElements(By.xpath("//*[contains(@text,'" + word + "')]"));
        Assert.assertTrue("List articles is not empty ",elementsList.isEmpty());
    }
}