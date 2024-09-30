import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AdminPanelTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testUserManagementNavigation() {
        // Perform login as admin first
        login();

        // Navigate to the dashboard and click "User Management" button
        WebElement userManagementButton = driver.findElement(By.id("user-management"));
        userManagementButton.click();

        // Wait for the URL to change
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/Admin/UserMangemnt"));

        // Verify navigation to User Management page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/Admin/UserMangemnt"), "Failed to navigate to User Management page.");
    }

    @Test
    public void testBookManagementNavigation() {
        // Perform login as admin first
        login();

        // Click on the "Book Management" button
        WebElement bookManagementButton = driver.findElement(By.id("book-management"));
        bookManagementButton.click();

        // Wait for the URL to change
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/Admin/BookManagemnt"));

        // Verify navigation to Book Management page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/Admin/BookManagemnt"), "Failed to navigate to Book Management page.");
    }

    @Test
    public void testTransactionsNavigation() {
        // Perform login as admin first
        login();

        // Click on the "Transactions" button
        WebElement transactionsButton = driver.findElement(By.id("tansaction-nav"));
        transactionsButton.click();

        // Wait for the URL to change
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/Admin/Transaction"));

        // Verify navigation to Transactions page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/Admin/Transaction"), "Failed to navigate to Transactions page.");
    }

    @Test
    public void testSearchFunctionality() {
        // Perform login as admin first
        login();

        // Enter a search keyword into the search bar and trigger search
        WebElement searchBar = driver.findElement(By.id("search-bar")); // Adjust ID/class as needed
        searchBar.sendKeys("Book Name");


        // Wait for the search results to be displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-res"))); // Adjust class as needed

        // Verify that search results are displayed
        assertTrue(searchResults.isDisplayed(), "Search results were not displayed.");
    }

    // Helper method to log in as admin
    
    private void login() {
        // Navigate to the sign-in page
        driver.get("http://localhost:5173/Authentication/signin");

        // Find the email and password fields, and the sign-in button
        WebElement emailField = driver.findElement(By.id("email"));  // Assuming email input has ID 'email'
        WebElement passwordField = driver.findElement(By.id("password")); // Assuming password input has ID 'password'
        WebElement signInButton = driver.findElement(By.id("login-button")); // Assuming button has ID 'signinButton'

        // Enter login credentials
        emailField.sendKeys("admin@email.com");
        passwordField.sendKeys("123456");
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

    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
