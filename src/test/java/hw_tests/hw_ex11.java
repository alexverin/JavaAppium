package hw_tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class hw_ex11 extends CoreTestCase {

    /**
     Ex11: Рефакторинг тестов
     Адаптировать под iOS тест на удаление одной сохраненной статьи из двух.
     Вместо проверки title-элемента придумать другой способ верификации оставшейся статьи
     (т.е. способ убедиться, что осталась в сохраненных ожидаемая статья).
     +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     Рефакторинг Ex8 (ex5): Тест: сохранение двух статей
     * Написать тест, который:
     * 1. Сохраняет две статьи в одну папку
     * 2. Удаляет одну из статей
     * 3. Убеждается, что вторая осталась*
     * 4. Переходит в неё и убеждается, что title совпадает
     */
    private static final
    String name_of_folder = "Learning programming";
    String firstSearch = "Java";
    String firstSearchSubstring = "Object-oriented programming language";
    String secondSearch = "IntelliJ IDEA";
    String secondSearchSubstring = "Integrated development environment";

    @Test
    public void testSaveTwoArticlesAndRemoveOne() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(firstSearch);
        SearchPageObject.clickByArticleWithSubstring(firstSearchSubstring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        ArticlePageObject.waitForTitleElement();
//        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid())
        {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS())
        {
            MyListsPageObject.clickCloseSyncArticlesAlert();
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        if(Platform.getInstance().isIOS())
        {
            SearchPageObject.waitForClearSearchInput();
        }
        SearchPageObject.typeSearchLine(secondSearch);
        SearchPageObject.clickByArticleWithSubstring(secondSearchSubstring);
        if (Platform.getInstance().isAndroid())
        {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();


        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        if(Platform.getInstance().isAndroid())
        {
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(firstSearch);

        MyListsPageObject.waitForArticleToDisappearByTitle(firstSearch);

        SearchPageObject.clickByArticleWithSubstring(secondSearch);
        String save_article = secondSearchSubstring;
        if(Platform.getInstance().isAndroid())
        {
            ArticlePageObject.waitForTitleElement();
        } else {
             ArticlePageObject.waitForKnownTitleElement(save_article);
        }

        ArticlePageObject.closeArticle();
    }
}



