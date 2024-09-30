import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NavbarTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Call login method before each test
        login();
    }

    @AfterEach
    public void tearDown() {
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
        WebElement signInButton = driver.findElement(By.id("login-button")); // Assuming button has ID 'login-button'

        // Enter login credentials
        emailField.sendKeys("testuser@example.com");
        passwordField.sendKeys("password123");
        signInButton.click();

        // Wait for the URL to contain the dashboard path (Assumed URL pattern after login)
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173"));

        // Pause the execution briefly for the page to load fully
        try {
            Thread.sleep(3000);  // Adjust sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOpenEditProfileModal() {
        // Find and click on the "Edit Profile" button
        WebElement editProfileButton = driver.findElement(By.id("edit-profile-button"));
        editProfileButton.click();

        // Wait until the modal opens and verify its presence
        WebElement modal = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-profile-modal")));

        assertTrue(modal.isDisplayed(), "Edit Profile modal should be visible.");
    }

    @Test
    public void testEditUserProfile() {
        // Open the Edit Profile modal
      

        WebElement editProfileButton = driver.findElement(By.id("edit-profile-button"));
        editProfileButton.click();

        // Wait for the modal to appear
        WebElement modal = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-profile-modal")));

        // Find the name field and update the value
        WebElement nameField = modal.findElement(By.id("name-input"));
        nameField.sendKeys(" 01");

        // Find the Save button and submit the form
        WebElement saveButton = modal.findElement(By.id("save-button"));
        saveButton.click();

        // Wait for the modal to disappear
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.invisibilityOf(modal));

        // Verify if the name is updated on the UI (Assuming the user's name is displayed in an element with id 'user-name')
        WebElement userNameDisplay = driver.findElement(By.id("user-name"));
        assertEquals("Test User 01", userNameDisplay.getText(), "The user's name should be updated.");
    }

    @Test
    public void testLogoutFunctionality() {
        // Find and click the logout button

        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();

        // Wait until the URL changes to the sign-in page
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173"));

        // Verify the user is redirected to the sign-in page
        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "User should be redirected to the sign-in page after logout.");
    }
}
