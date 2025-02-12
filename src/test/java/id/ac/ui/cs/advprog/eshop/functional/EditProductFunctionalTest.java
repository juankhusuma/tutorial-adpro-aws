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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@ExtendWith(SeleniumJupiter.class)
public class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    private String createProductAndGetId(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        nameInput.sendKeys("Test Product");
        quantityInput.sendKeys("100");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement editButton = driver.findElement(By.cssSelector("a[href*='/product/edit/']"));
        String href = editButton.getAttribute("href");
        return href.substring(href.lastIndexOf('/') + 1);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        String productId = createProductAndGetId(driver);
        driver.get(baseUrl + "/product/edit/" + productId);

        String pageTitle = driver.getTitle();
        assertEquals("Edit Product", pageTitle);
    }

    @Test
    void editProduct_isSuccessful(ChromeDriver driver) throws Exception {
        String productId = createProductAndGetId(driver);
        driver.get(baseUrl + "/product/edit/" + productId);

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.clear();
        nameInput.sendKeys("Updated Product Name");
        quantityInput.clear();
        quantityInput.sendKeys("200");

        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        assertTrue(driver.getCurrentUrl().contains("/product/list"));
        assertTrue(driver.getPageSource().contains("Updated Product Name"));
        assertTrue(driver.getPageSource().contains("200"));
    }

    @Test
    void editProduct_formElements_arePresent(ChromeDriver driver) throws Exception {
        String productId = createProductAndGetId(driver);
        driver.get(baseUrl + "/product/edit/" + productId);

        WebElement form = driver.findElement(By.tagName("form"));
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement cancelButton = driver.findElement(By.cssSelector("a.btn-secondary"));

        assertTrue(form.getAttribute("action").contains("/product/edit/"));
        assertEquals("post", form.getAttribute("method").toLowerCase());

        assertEquals("text", nameInput.getAttribute("type"));
        assertEquals("text", quantityInput.getAttribute("type"));

        assertEquals("Update", submitButton.getText());
        assertEquals("Cancel", cancelButton.getText());
    }

    @Test
    void editProduct_cancelButton_redirectsToList(ChromeDriver driver) throws Exception {
        String productId = createProductAndGetId(driver);
        driver.get(baseUrl + "/product/edit/" + productId);

        WebElement cancelButton = driver.findElement(By.cssSelector("a.btn-secondary"));
        cancelButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        assertTrue(driver.getCurrentUrl().contains("/product/list"));
    }

    @Test
    void editProduct_headerText_isCorrect(ChromeDriver driver) throws Exception {
        String productId = createProductAndGetId(driver);
        driver.get(baseUrl + "/product/edit/" + productId);

        WebElement header = driver.findElement(By.tagName("h3"));
        assertEquals("Edit Product", header.getText());
    }
}