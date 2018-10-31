package hw_tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class hw_ex5 {

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
    public void tearDown() {
        driver.quit();
    }

    /**  Ex5: Тест: сохранение двух статей
     * Написать тест, который:
     * 1. Сохраняет две статьи в одну папку
     * 2. Удаляет одну из статей
     * 3. Убеждается, что вторая осталась
     * 4. Переходит в неё и убеждается, что title совпадает
     */

    @Test
    public void saveTwoArticlesAndRemoveOne()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find 'Search Wikipedia' input",
                5
        );
        String first_search = "Appium";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                first_search,
                "Can't find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_search + "']"),
                "Can't find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find More options Button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article",
                5
        );

        String nameOfFolder = "Learning automation";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'OK')]"),
                "Cannot find 'OK' button",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find 'Search Wikipedia' input",
                15
        );
        String second_search = "IntelliJ IDEA";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                second_search,
                "Can't find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_search + "']"),
                "Can't find 'Search Wikipedia' input",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find More options Button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find My List navigation button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/reading_list_list']//*[@text='" + nameOfFolder + "']"),
                "Cannot find created folder",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + first_search + "']"),
                "Cannot find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='" + first_search + "']"),
                "Cannot delete saved article ",
                5
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "/n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }


    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();

        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action.
                press(PointOption.point(right_x, middle_y)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))).
                moveTo(PointOption.point(left_x, middle_y)).
                release().
                perform();
    }
}