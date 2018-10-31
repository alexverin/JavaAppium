package hw_tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class hw_ex12 extends CoreTestCase {


    /**
     * Ex12*: Рефакторинг тестов
     * Адаптировать по iOS тест на поиск и верификацию трех результатов выдачи поиска.
     * Рефакторинг Ex9*: Рефакторинг темплейта
     * * В одном из занятий четвертого урока упоминается о методе темплейтов. Там рассказано, как работать с локаторами, которые зависят от подстроки SUBSTRING.
     * * В примере из теста у нас всего одна подстрока. Но подобные локаторы можно строить с любым количеством подстрок.
     * * В приложении Wikipedia результатом поиска является набор ссылок на статьи, и каждая ссылка содержит как заголовок статьи, так и краткое описание.
     * * Например, для запроса “Java” одним из результатов выдачи будет “Java (Programming language)” и описание “Object-oriented programming language”.
     * * Задача:
     * * 1. Подобрать локатор, который находит результат поиска одновременно по заголовку и описанию (если заголовок или описание отличается - элемент не находится).
     * * 2. Добавить соответствующий метод в секцию TEMPLATES METHODS класса SearchPageObjectFactory.
     * * 3. В этот же класс добавить метод waitForElementByTitleAndDescription(String title, String description). Он должен дожидаться результата поиска по двум строкам - по заголовку и описанию.
     * * Если такой элемент не появляется, тест должен упасть с читаемой и понятной ошибкой.
     * * 4. Написать тест, который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как минимум 3 результата).
     * * 5. Далее тест должен убеждаться, что первых три результата присутствуют в результате поиска.
     */



    @Test
    public void testArticlesWithKeyWord()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Google";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found less than 3 search results",
                amount_of_search_results > 3
        );
        List<WebElement> results = SearchPageObject.findAllResultsTitles();
        for(WebElement result : results) {
            String text = result.getAttribute("name");
            boolean text_found = text.contains("Google");
            assertTrue(
                    "Result " + text + " doesn't match the request",
                    text_found
            );
        }
    }
}
