package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@ExtendWith(SeleniumJupiter.class)
public class ProductListFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);
    }

    @Test
    void createProductButton_redirectsToCreatePage(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        WebElement createButton = driver.findElement(By.cssSelector("a[href*='/product/create']"));
        createButton.click();
        assertTrue(driver.getCurrentUrl().contains("/product/create"));
    }

    @Test
    void listPage_displaysProducts(ChromeDriver driver) throws Exception {
        createTestProduct(driver, "Test Product", "100");
        driver.get(baseUrl + "/product/list");
        WebElement table = driver.findElement(By.cssSelector("table"));
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        assertFalse(rows.isEmpty());
        WebElement firstRow = rows.get(0);
        assertTrue(firstRow.getText().contains("Test Product"));
        assertTrue(firstRow.getText().contains("100"));
    }

    @Test
    void editButton_redirectsToEditPage(ChromeDriver driver) throws Exception {
        createTestProduct(driver, "Test Product", "100");
        driver.get(baseUrl + "/product/list");
        WebElement editButton = driver.findElement(By.cssSelector("a[href*='/product/edit/']"));
        editButton.click();
        assertTrue(driver.getCurrentUrl().contains("/product/edit/"));
    }

    @Test
    void deleteButton_removesProduct(ChromeDriver driver) throws Exception {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        createTestProduct(driver, "Product to Delete", "100");

        driver.get(baseUrl + "/product/list");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody tr")));

        assertTrue(driver.getPageSource().contains("Product to Delete"));
        List<WebElement> initialRows = driver.findElements(By.cssSelector("tbody tr"));
        int initialCount = initialRows.size();

        WebElement targetRow = findRowByProductName(driver, "Product to Delete");
        WebElement deleteButton = targetRow.findElement(By.cssSelector(".btn-danger"));
        deleteButton.click();

        Thread.sleep(500);
        assertFalse(driver.getPageSource().contains("Product to Delete"));
        List<WebElement> remainingRows = driver.findElements(By.cssSelector("tbody tr"));
        assertEquals(initialCount - 1, remainingRows.size());
    }

    @Test
    void tableHeaders_areCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        List<WebElement> headers = driver.findElements(By.cssSelector("th"));
        assertEquals(3, headers.size());
        assertEquals("Product Name", headers.get(0).getText());
        assertEquals("Quantity", headers.get(1).getText());
        assertEquals("Action", headers.get(2).getText());
    }

    @Test
    void pageHeader_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        WebElement header = driver.findElement(By.tagName("h2"));
        assertEquals("Product' List", header.getText());
    }

    private WebElement findRowByProductName(ChromeDriver driver, String productName) {
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                return row;
            }
        }
        throw new RuntimeException("Product row not found: " + productName);
    }

    private void createTestProduct(ChromeDriver driver, String name, String quantity) {
        driver.get(baseUrl + "/product/create");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nameInput")));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        nameInput.sendKeys(name);
        quantityInput.sendKeys(quantity);
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        wait.until(ExpectedConditions.urlContains("/product/list"));
    }
}