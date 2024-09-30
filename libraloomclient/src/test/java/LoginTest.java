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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Navigate to the login page
        driver.get("http://localhost:5173/Authentication/signin");
        
        // Enter valid credentials
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");

        // Click the login button
        driver.findElement(By.id("login-button")).click();

        // Wait for navigation to the dashboard
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("http://localhost:5173/"));  
        
        // Verify that the user is redirected to the dashboard
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("http://localhost:5173/"), "User was not redirected to the dashboard after login.");
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        // Navigate to the login page
        driver.get("http://localhost:5173/Authentication/signin");
        
        // Enter valid email and incorrect password
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");

        // Click the login button
        driver.findElement(By.id("login-button")).click();

        // Wait for the SweetAlert pop-up to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the SweetAlert error title to appear
        WebElement sweetAlertTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login failed!')]")) // Adjust the title text
        );

        // Wait for the SweetAlert error message to appear
        WebElement sweetAlertText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Invalid Email or Password')]")) // Adjust the message text
        );

        // Assert that the SweetAlert title and text are displayed
        assertTrue(sweetAlertTitle.isDisplayed(), "SweetAlert error title should be visible");
        assertTrue(sweetAlertText.isDisplayed(), "SweetAlert error message should be visible");

        // Optionally, click the SweetAlert OK button to close it
       // Adjust class name as necessary
    }

    @Test
    public void testLoginWithNonExistentEmail() {
        // Navigate to the login page
        driver.get("http://localhost:5173/Authentication/signin");
        
        // Enter a non-existent email and a valid password
        driver.findElement(By.id("email")).sendKeys("nonexistent@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");

        // Click the login button
        driver.findElement(By.id("login-button")).click();

        // Wait for the SweetAlert pop-up to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the SweetAlert error title to appear
        WebElement sweetAlertTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Login failed!')]")) // Adjust the title text
        );

        // Wait for the SweetAlert error message to appear
        WebElement sweetAlertText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Internal server error occurred')]")) // Adjust the message text
        );

        // Assert that the SweetAlert title and text are displayed
        assertTrue(sweetAlertTitle.isDisplayed(), "SweetAlert error title should be visible");
        assertTrue(sweetAlertText.isDisplayed(), "SweetAlert error message should be visible");

       
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Navigate to the login page
        driver.get("http://localhost:5173/Authentication/signin");
        
        // Click the login button without entering credentials
        driver.findElement(By.id("login-button")).click();
    
        // Wait for the helper text elements to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
        // Wait for the helper text for the email field
        WebElement emailHelperText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("email-helper-text")) // Adjust the ID or selector as necessary
        );
    
        // Wait for the helper text for the password field
        WebElement passwordHelperText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("password-helper-text")) // Adjust the ID or selector as necessary
        );
    
        // Assert that the helper texts are displayed
        assertTrue(emailHelperText.isDisplayed(), "Helper text for email should be visible");
        assertTrue(passwordHelperText.isDisplayed(), "Helper text for password should be visible");
    
        // Optionally, assert the expected text in the helper messages
        assertEquals("Email is Required", emailHelperText.getText(), "Email helper text should indicate that email is required.");
        assertEquals("Password is Required", passwordHelperText.getText(), "Password helper text should indicate that password is required.");
    }
    

    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
