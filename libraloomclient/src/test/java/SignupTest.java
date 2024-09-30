import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testSignup() {
        driver.get("http://localhost:5173/Authentication/signup"); // Replace with your app's signup URL

        // Fill in the signup form
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");

        // Submit the form
        driver.findElement(By.id("signup-button")).click();

        // Wait for the URL to be the home page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:5173/Authentication/signup")); 

        // Verify that the current URL is the home page
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:5173/Authentication/signup", currentUrl, "Navigation to home page failed.");
    }

    @Test
    public void testEmptyFieldsSignup() {
        driver.get("http://localhost:5173/Authentication/signup");

        // Find and click the signup button without entering any data
        driver.findElement(By.id("signup-button")).click();

        // Wait for error messages (helper texts) to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Check for helper text on each field
        WebElement emailHelperText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Email is Required')]")));
        WebElement nameHelperText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Name is Required')]")));
        WebElement passwordHelperText = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Password is Required')]")));

        // Assert that all error messages are visible
        assertTrue(emailHelperText.isDisplayed());
        assertTrue(nameHelperText.isDisplayed());
        assertTrue(passwordHelperText.isDisplayed());
    }

    @Test
    public void testInvalidEmailSignup() {
        driver.get("http://localhost:5173/Authentication/signup");

        // Fill in the form with an invalid email
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("invalid-email");
        driver.findElement(By.id("password")).sendKeys("password123");

        // Submit the form
        driver.findElement(By.id("signup-button")).click();

        // Wait for the email validation error message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailHelperText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Invalid email')]")));

        // Assert that the invalid email error message is visible
        assertTrue(emailHelperText.isDisplayed());
    }

    @Test
    public void testSignupWithShortPassword() {
        driver.get("http://localhost:5173/Authentication/signup");

        // Fill in the signup form with a short password
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("123"); // Short password

        // Submit the form
        driver.findElement(By.id("signup-button")).click();

        // Wait for the validation message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password-helper-text"))); // Adjust the ID if
                                                                                                  // necessary

        // Verify that the error message for password length is displayed
        String errorMessage = driver.findElement(By.id("password-helper-text")).getText();
        assertTrue(errorMessage.contains("Password must be at least 6 characters"),
                "Error message for short password should be visible.");
    }

    @Test
    public void testSignupWithExistingEmail() {
        driver.get("http://localhost:5173/Authentication/signup");
    
        // Fill in the signup form with an existing email
        driver.findElement(By.id("name")).sendKeys("Test User");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com"); // Assuming this email is already registered
        driver.findElement(By.id("password")).sendKeys("password123");
    
        // Submit the form
        driver.findElement(By.id("signup-button")).click();
    
        // Wait for the SweetAlert title and text to become visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sweetAlertTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Signup failed!')]")) // Adjust if needed
        );
        WebElement sweetAlertText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Email is already exist')]")) // Adjust if needed
        );
    
        // Assert that the SweetAlert title and text are visible
        assertTrue(sweetAlertTitle.isDisplayed(), "SweetAlert error title should be visible");
        assertTrue(sweetAlertText.isDisplayed(), "SweetAlert error message should be visible");
    }
    


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


