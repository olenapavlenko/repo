package search_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.SearchPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchError_Test {
    protected String baseUrl = "https://edition.cnn.com/";
    protected WebDriver driver;
    protected SearchPage searchPage;
    SoftAssert softAssert;


    @BeforeMethod
    public void setUp() {

        this.driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(this.baseUrl);
        searchPage = new HomePage(driver).clickOnSearch();
        softAssert = new SoftAssert();


    }

    @Test
    public void search_happyPath() {
        searchPage.executeSearch("nfc");
        new SearchPage(driver).getSearchResult_Title().forEach(x -> softAssert.assertFalse(x.isEmpty(), "menu item has no text"));
        int messageIndicator = new SearchPage(driver).getSearchResultEmptyMsg().size();

        softAssert.assertTrue(messageIndicator == 0, "error container present");
        softAssert.assertAll();


    }

    @Test
    public void search_withoutResult() {
        String browse = "nfcFake";
        searchPage = new SearchPage(driver).executeSearch(browse);
        List<WebElement> errorMessage = new SearchPage(driver).getSearchResultEmptyMsg();
        int messageIndicator = errorMessage.size();
        softAssert.assertFalse(messageIndicator == 0, "error message missing");
        String[] messageContent = errorMessage.get(0).getText()
                .split("\\n");
        softAssert.assertEquals(messageContent[0], "Your search for " + browse + " did not match any documents.");
        softAssert.assertEquals(messageContent[1], "A few suggestions");
        softAssert.assertEquals(messageContent[2], "Make sure all words are spelled correctly");
        softAssert.assertEquals(messageContent[3], "Try different keywords");
        softAssert.assertEquals(messageContent[4], "Try more general keywords");
        softAssert.assertAll();
    }

    @AfterMethod
    public void clearSearch() {
        new SearchPage(this.driver).clearSearch();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();

    }
}
