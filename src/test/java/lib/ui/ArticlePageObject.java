package lib.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            ARTICLE_SUB_TITLE;


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static String getPageSubtitleTitle(String title)
    {
        return ARTICLE_SUB_TITLE.replace("{SUBSTRING}",title);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Can't find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getAttribute("name");
        }
    }


    public void scrollWebPageUp()
    {
        if (Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void checkTitleElementImmediately() {
        this.assertElementPresent(
                TITLE,
                "Cannot find article title immediately");
    }
    public void checkSubTitleElement() {
        this.assertElementPresent(
                ARTICLE_SUB_TITLE,
                "Cannot find article subtitle");
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }

    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find More options Button",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' button",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder ",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot find 'OK' button",
                5
        );
    }

    public void addArticlesToMySaved() {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Can't find option to add article to reading list",
                5
        );
    }

    //    public void addArticleToSavedList(String name_of_folder)
//    {
//        this.waitForElementAndClick(
//                OPTIONS_BUTTON,
//                "cannot find button to open article options",
//                5);
//
//        this.waitForElementAndClick(
//                OPTIONS_ADD_TO_MY_LIST_BUTTON,
//                "cannot find option 'add to reading list'",
//                5);
//
//        this.waitForElementAndClick(By.xpath("//*[@text ='" + name_of_folder + "']"),
//                "cannot find folder" + name_of_folder,
//                10);
//    }
    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X button",
                5
        );
    }
    public WebElement waitForKnownTitleElement(String title)
    {
        return this.waitForElementPresent(getPageSubtitleTitle(title), "Cannot find known article title on page", 20);
    }
}
