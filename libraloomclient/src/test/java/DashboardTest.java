import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DashboardTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Open the browser in full screen
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        // Close the browser after all tests
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        // Navigate to the sign-in page
        driver.get("http://localhost:5173/Authentication/signin");

        // Find the email and password fields, and the sign-in button
        WebElement emailField = driver.findElement(By.id("email"));  // Assuming email input has ID 'email'
        WebElement passwordField = driver.findElement(By.id("password")); // Assuming password input has ID 'password'
        WebElement signInButton = driver.findElement(By.id("login-button")); // Assuming button has ID 'signinButton'

        // Enter login credentials
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("password123");
        signInButton.click();

        // Wait for the URL to contain the dashboard path
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173"));
        
        // Pause the execution for 10 seconds
        try {
            Thread.sleep(10000);  // Wait for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
public void testSearchFunctionality() {
    login();  // Log in before testing search functionality

    // Test search functionality
    WebElement searchInput = driver.findElement(By.id("search-input"));
    WebElement searchButton = driver.findElement(By.id("search-icon"));

    // Enter a keyword and perform the search
    searchInput.sendKeys("Test Book");
    searchButton.click();

    // Wait for the search results to be displayed in the specific div
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement searchResult = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("book-displayer")) // Waiting for the div with id 'book-displayer'
    );

    // Verify search result is displayed
    assertTrue(searchResult.isDisplayed(), "Search results should be visible");
}
@Test
public void testReturnBookFunctionality() {
    login();  // Log in before testing return book functionality

    // Find the return book button and click it
    WebElement returnBookButton = driver.findElement(By.id("return-button"));
    returnBookButton.click();

    // Wait for the modal to appear and confirm the return
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement returnModal = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("return-confirm"))
    );

    // Confirm return by clicking the "Return" button in the modal
    WebElement confirmReturnButton = driver.findElement(By.id("confirm-return-button"));
    confirmReturnButton.click();

    // Wait for the SweetAlert success message to appear
    WebElement sweetAlertTitle = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Success!')]"))
    );
    WebElement sweetAlertText = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'successfully returned.')]"))
    );

   
    assertTrue(sweetAlertTitle.isDisplayed(), "SweetAlert success title should be visible");
    assertTrue(sweetAlertText.isDisplayed(), "SweetAlert success message should be visible");

}
}
