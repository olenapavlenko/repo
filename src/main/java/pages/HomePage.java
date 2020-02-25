package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.xpath;

public class HomePage {
    protected WebDriver driver;
    WebDriverWait wait;


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    protected By upperPane = xpath(".//div[@id='nav__plain-header']");
    protected By searchBtn = upperPane.xpath(".//*[@data-test='searchButton']");
    protected By searchBlock = xpath(".//div[contains(@class,'iK')]");


    public SearchPage clickOnSearch() {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(upperPane));
        driver.findElement(searchBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                searchBlock));

        return new SearchPage(driver);
    }

}
