package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_SYNC_ARTICLE_DIALOG,
            READING_LISTS_TAB,
            EDIT_BUTTON,
            REMOVE_BUTTON;



    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }




    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Can't find folder by name" + name_of_folder,
                5
        );
    }

    public void openReadingLists()
    {
        this.waitForElementAndClick(
                READING_LISTS_TAB,
                "cannot find reading lists tab",
                10
        );
    }
    public void waitForFolderPresent(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                15
        );
    }
    public void waitForArticleToAppearByTitle (String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Can't find saved article by title " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle (String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

    public void clickCloseSyncArticlesAlert()
    {
        this.waitForElementAndClick(
                CLOSE_SYNC_ARTICLE_DIALOG,
                "Can't find and click search Cancel button",
                5
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Can't find saved article"
        );
        if(Platform.getInstance().isIOS())
        {
            this.clickElementToTheRightUpperCorner(article_xpath, "Can't find saved article");
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }
    public void deleteSavedArticle(String article_title)
    {
        this.waitForElementAndClick(EDIT_BUTTON, "Can't fing edit button", 5);
        this.waitForElementAndClick(
                getSavedArticleXpathByTitle(article_title),
                "Can't find saved article" + article_title,
                5
        );
        this.waitForElementAndClick(REMOVE_BUTTON, "Can't find remove button", 5);
    }
}
