package hw_tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class hw_ex18 extends CoreTestCase {

/** Ex18*: Рефакторинг
Адаптировать по MW тест на поиск и верификацию трех результатов выдачи поиска
 .*/
    @Test
    public void testMultipleConditionsSearch() throws Exception {
        String[] search_results = {
                "Java",
                "Java (programming language)",
                "JavaScript"
        };

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_results[0]);
        List<WebElement> target_results = searchPageObject.findAllResultsTitles();
        assertTrue(target_results.size() > 2);

        for(int i=0; i<3; i++) {
            assertTrue("element "  + i + " is not correct search result", target_results.get(i).getText().contains(search_results[i]));
        }
    }
}
