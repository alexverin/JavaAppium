package hw_tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class hw_ex17 extends CoreTestCase {
/**
 * Ex17: Рефакторинг
 * Адаптировать под MW тест на удаление одной сохраненной статьи из двух.
 * Вместо проверки title-элемента придумать другой способ верификации оставшейся статьи
 * (т.е. способ убедиться, что осталась в сохраненных ожидаемая статья).
 *
 * Рефакторинг
 */

private static final
String name_of_folder = "Learning programming",
    firstSearch = "Java",
    firstSearchSubstring = "bject-oriented programming language",
    secondSearch = "IntelliJ IDEA",
    secondSearchSubstring = "Integrated development environment",
        login = "fedorsumkin",
        password = "12qwaszx";


@Test
public void testSaveTwoArticlesAndRemoveOne() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(firstSearch);
        SearchPageObject.clickByArticleWithSubstring(firstSearchSubstring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()){
            ArticlePageObject.waitForKnownTitleElement("Java (programming language)");
        } else
        {
            ArticlePageObject.waitForTitleElement();
        }
        if (Platform.getInstance().isMW()){
            ArticlePageObject.addArticlesToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            Thread.sleep(5000);
            ArticlePageObject.addArticlesToMySaved();
        } else {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchInput();
        SearchPageObject.typeSearchLine(secondSearch);
        SearchPageObject.clickByArticleWithSubstring(secondSearchSubstring);
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        ArticlePageObject.addArticlesToMySaved();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        Thread.sleep(2000);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        String delete_article = "Antonio Vivaldi";
        String save_article = "Wolfgang Amadeus Mozart";
        if (Platform.getInstance().isMW()){
            MyListsPageObject.swipeByArticleToDelete(delete_article);
        }else {
            if(Platform.getInstance().isIOS()){
                MyListsPageObject.openReadingLists();
            }
            MyListsPageObject.waitForFolderPresent(name_of_folder);
            MyListsPageObject.openFolderByName(name_of_folder);

            if(Platform.getInstance().isAndroid()) {
                MyListsPageObject.swipeByArticleToDelete(delete_article);
            }
            else {
                MyListsPageObject.deleteSavedArticle(delete_article);
            }
            MyListsPageObject.waitForArticleToDisappearByTitle(delete_article);
        }

        MyListsPageObject.openFolderByName(save_article);
        if(Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForKnownTitleElement(save_article);
        }
        else{
            String title_found = ArticlePageObject.getArticleTitle();
            assertEquals(
                    "Page title doesn't match saved",
                    save_article,
                    title_found
            );
        }}
    }