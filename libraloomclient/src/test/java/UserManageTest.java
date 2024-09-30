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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

public class UserManageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

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

    @Test
    public void testUserDeletion() {
        login(); // Call the login method before each test

        WebElement userManagementButton = driver.findElement(By.id("user-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/UserMangemnt"));

        // Click on the delete button for the first user in the list
        WebElement deleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-button")));
        deleteButton.click();

        // Confirm deletion in SweetAlert
        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Yes, delete it!')]")));
        confirmButton.click();

        // Verify the deletion success message
        WebElement sweetAlertTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Deleted!')]")));
        WebElement sweetAlertText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Book has been deleted.')]")));

        assertTrue(sweetAlertTitle.isDisplayed());
        assertTrue(sweetAlertText.isDisplayed());
    }

    @Test
    public void testSearchUserByName() {
        login(); // Call the login method before each test

        WebElement userManagementButton = driver.findElement(By.id("user-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/UserMangemnt"));

        // Set search criteria to search by name
        // WebElement searchByNameRadio = driver.findElement(By.id("search-name"));
        // searchByNameRadio.click();

       driver.findElement(By.id("serach-input")).sendKeys("John Doe"); // Replace with a valid user name

     

        // Verify user is displayed in the results
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Name : John Doe')]")));
        assertTrue(userName.isDisplayed());
    }

    @Test
    public void testSearchUserById() {
        login(); // Call the login method before each test

        WebElement userManagementButton = driver.findElement(By.id("user-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/UserMangemnt"));

        // Set search criteria to search by ID
        WebElement searchByIdRadio = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='id']")));
        searchByIdRadio.click();

        // Input search term
        WebElement searchInput = driver.findElement(By.xpath("//input[@type='text']"));
        searchInput.sendKeys("12345"); // Replace with a valid user ID

        // Click on Search button
        WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(), 'Search')]"));
        searchButton.click();

        // Verify user is displayed in the results
        WebElement userId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'ID : 12345')]"))); // Adjust based on your UI
        assertTrue(userId.isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
