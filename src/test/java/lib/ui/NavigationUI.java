package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

     protected static String
        MY_LISTS_LINK;

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find My List navigation button",
                10
        );
    }
}
