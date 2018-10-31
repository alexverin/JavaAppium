package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeCell/XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        CLOSE_SYNC_ARTICLE_DIALOG = "id:places auth close";

    }
    public iOSMyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
