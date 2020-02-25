package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends HomePage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }


    protected By searchInput = searchBlock.xpath(".//input");
    protected By searchSubmit = searchBlock.xpath(".//button/div");
    protected By searchInputClear = searchBlock.xpath(".//button[contains(.,'Clear')]");
    protected By searchResultsList = searchBlock.xpath(".//*[contains(@class,'results-list')]");
    protected By searchResultEmptyMsg = By.xpath(".//*[contains(@class,'no-results--returned')]");
    protected By searchResultsCount = By.xpath(".//*[@class='cnn-search__results']");

    protected By searchResult_title = By.xpath(".//h3//a");

    public SearchPage executeSearch(String string) {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(string);
        driver.findElement(searchSubmit).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResultsList));
        return new SearchPage(driver);
    }

    public SearchPage clearSearch() {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputClear)).click();

        return this;
    }

    public List<WebElement> getSearchResultsList() {
        wait = new WebDriverWait(driver, 7);
        return wait.until(ExpectedConditions
                .presenceOfElementLocated(searchResultsList))
                .findElements(searchResultsList.xpath("./div"));

    }

    public List<WebElement> getSearchResultEmptyMsg(){
        wait = new WebDriverWait(driver, 7);
        return wait.until(ExpectedConditions
                .presenceOfElementLocated(searchResultsCount))
                .findElements(searchResultEmptyMsg);
    }


    public List<String> getSearchResult_Title() {
        return this.getSearchResultsList().stream().map(x -> x.findElement(searchResult_title).getText()).collect(Collectors.toList());

    }


}
