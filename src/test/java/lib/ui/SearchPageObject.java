package lib.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_CANCEL_MINI,
            SEARCH_RESULT_TITLE;
    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getElementXpathByTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /*TEMPLATE METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,"Can't find and click search init element", 5 );
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Can't find search in put after clicking search init element");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Can't find Cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search Cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Can't find and click search Cancel button", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Can't find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Can't find search result with substring" + substring );
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Can't find and click search result with substring" + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Can't find anything by the request",
                15
        );
        return this.getAmountOfElements (SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Can't find empty result element", 15);

    }
    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "Not supposed to find any results");
    }
    public void waitForElementByTitleAndDescription(String article_title, String article_description)
    {
        String element_xpath = getElementXpathByTitleAndDescription(article_title, article_description);
        this.waitForElementPresent(element_xpath, "Cannot find result with title "+article_title+" and description "+article_description);
    }
    public void waitForClearSearchInput()
    {
        this.waitForElementAndClick(
                SEARCH_CANCEL_MINI,
                "Cannot find mini cross button",
                5
        );
    }
    public List<WebElement> findAllResultsTitles()
    {
        return this.waitForAllElementsPresent(SEARCH_RESULT_TITLE,"Cannot find search results", 15);
    }
}
