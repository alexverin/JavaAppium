package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTest extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming",
            firstSearch = "Java",
            firstSearchSubstring = "bject-oriented programming language",
            secondSearch = "IntelliJ IDEA",
            secondSearchSubstring = "Integrated development environment",
            login = "fedorsumkin",
            password = "12qwaszx";
    ;
//    @Test
//    public void testSaveFirstArticleToMyList()
//    {
//        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
//
//        SearchPageObject.initSearchInput();
//        SearchPageObject.typeSearchLine(firstSearch);
//        SearchPageObject.clickByArticleWithSubstring(firstSearchSubstring);
//
//        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
//
//        ArticlePageObject.waitForTitleElement();
//        String article_title = ArticlePageObject.getArticleTitle();
//
//        if (Platform.getInstance().isAndroid())
//        {
//            ArticlePageObject.addArticleToMyList(name_of_folder);
//        } else {
//            ArticlePageObject.addArticlesToMySaved();
//        }
//        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
//        if(Platform.getInstance().isIOS())
//        {
//            MyListsPageObject.clickCloseSyncArticlesAlert();
//        }
//
//        ArticlePageObject.closeArticle();
//
//        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
//        NavigationUI.clickMyLists();
//
//        if(Platform.getInstance().isAndroid())
//        {
//            MyListsPageObject.openFolderByName(name_of_folder);
//        }
//        MyListsPageObject.swipeByArticleToDelete(article_title);
//    }
//}
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        Thread.sleep(2000);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(firstSearch);
        SearchPageObject.clickByArticleWithSubstring(firstSearchSubstring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title;
        if (Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForKnownTitleElement(firstSearchSubstring);
            article_title = "Java (programming language)";
        } else {
            ArticlePageObject.waitForTitleElement();
            article_title = ArticlePageObject.getArticleTitle();
        }
        if (Platform.getInstance().isMW()) {
            ArticlePageObject.addArticlesToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            Assert.assertEquals("We are not on the same page after login.",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
            ArticlePageObject.addArticlesToMySaved();
        } else {
            String name_of_folder = "Learning programming";
            ArticlePageObject.addArticleToMyList(name_of_folder);
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        Thread.sleep(2000);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isMW()) {
            MyListsPageObject.swipeByArticleToDelete(article_title);
        } else {
            if (Platform.getInstance().isIOS()) {
                MyListsPageObject.openReadingLists();
            }
            MyListsPageObject.waitForFolderPresent(name_of_folder);
            MyListsPageObject.openFolderByName(name_of_folder);
            if (Platform.getInstance().isAndroid()) {
                MyListsPageObject.swipeByArticleToDelete(article_title);
            } else {
                MyListsPageObject.deleteSavedArticle(article_title);
            }
        }}}
