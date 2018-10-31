package hw_tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;


public class hw_ex8 extends CoreTestCase {
    /** Ex8: Рефакторинг тестов
     * Отрефакторить тесты, написанные в предыдущих занятиях (Ex3, Ex5, Ex6) под текущую структуру тестов.
     ===================================================================================================
     * Рефакторинг Ex3: Тест: отмена поиска
     * Написать тест, который:
     * 1. Ищет какое-то слово
     * 2. Убеждается, что найдено несколько статей
     * 3. Отменяет поиск
     * 4. Убеждается, что результат поиска пропал
     */

    @Test
    public void testCancelResultOfSearchSeveralArticles()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "Found few results!",
                amount_of_search_results > 0
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    /**  Рефакторинг Ex5: Тест: сохранение двух статей
     * Написать тест, который:
     * 1. Сохраняет две статьи в одну папку
     * 2. Удаляет одну из статей
     * 3. Убеждается, что вторая осталась*
     * 4. Переходит в неё и убеждается, что title совпадает
     */
    @Test
    public void testSaveTwoArticlesAndRemoveOne() {

        String firstSearch = "Appium";
        String secondSearch = "IntelliJ IDEA";
        String name_of_folder = "Learning automation";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(firstSearch);
        SearchPageObject.clickByArticleWithSubstring(firstSearch);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(secondSearch);
        SearchPageObject.clickByArticleWithSubstring(secondSearch);

        ArticlePageObject.waitForTitleElement();
//        ArticlePageObject.addArticleToSavedList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(firstSearch);
        MyListsPageObject.waitForArticleToDisappearByTitle(firstSearch);

        SearchPageObject.clickByArticleWithSubstring(secondSearch);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.closeArticle();
    }

    /** Рефакторинг Ex6: Тест: assert title
     * Написать тест, который
     * 1. Oткрывает статью и убеждается, что у нее есть элемент title.
     * 2. Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
     * 3. Если title не найден - тест падает с ошибкой.
     * 4. Метод можно назвать assertElementPresent
     */

    @Test
    public void testCheckArticleTitleAvailability()
    {
        String search_word = "Appium";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_word);
        SearchPageObject.clickByArticleWithSubstring(search_word);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.checkTitleElementImmediately();
    }
}