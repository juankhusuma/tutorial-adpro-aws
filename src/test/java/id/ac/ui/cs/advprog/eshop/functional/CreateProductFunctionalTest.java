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
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Sample Product");
        quantityInput.sendKeys("100");

        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        assertTrue(driver.getCurrentUrl().contains("/product/list"));
        assertTrue(driver.getPageSource().contains("Sample Product"));
        assertTrue(driver.getPageSource().contains("100"));
    }

    @Test
    void createProduct_formElements_arePresent(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement form = driver.findElement(By.tagName("form"));
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        assertEquals("post", form.getAttribute("method").toLowerCase());

        assertEquals("text", nameInput.getAttribute("type"));
        assertEquals("text", quantityInput.getAttribute("type"));

        assertEquals("Enter product name", nameInput.getAttribute("placeholder"));
        assertEquals("Enter quantity name", quantityInput.getAttribute("placeholder"));

        WebElement nameLabel = driver.findElement(By.cssSelector("label[for='nameInput']"));
        WebElement quantityLabel = driver.findElement(By.cssSelector("label[for='quantityInput']"));
        assertEquals("Name", nameLabel.getText());
        assertEquals("Quantity", quantityLabel.getText());

        assertEquals("Submit", submitButton.getText());
    }

    @Test
    void createProduct_headerText_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        WebElement header = driver.findElement(By.tagName("h3"));
        assertEquals("Create New Product", header.getText());
    }
}