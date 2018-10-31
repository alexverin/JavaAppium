package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "//xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_CANCEL_MINI = "id:clear mini";
        SEARCH_RESULT_TITLE = "xpath://XCUIElementTypeCell/XCUIElementTypeLink";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']/*[./*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{SUBSTRING_TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SUBSTRING_DESCRIPTION}']]";
    }

    public iOSSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
