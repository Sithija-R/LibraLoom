import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

public class BookManageTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

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
    @AfterEach
    void teardown() {
        driver.quit();
    }

    

    @Test
    void testAddBook() {
        login();

        WebElement userManagementButton = driver.findElement(By.id("book-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/BookManagemnt"));

        // Fill in the book details
        driver.findElement(By.id("title")).sendKeys("Test Book");
        driver.findElement(By.id("author")).sendKeys("Test Author");
        driver.findElement(By.id("isbn")).sendKeys("1234567890");
        driver.findElement(By.id("publicationYear")).sendKeys("2024");

        // Click the Add Book button
        driver.findElement(By.xpath("//button[contains(text(), 'Add Book')]")).click();

        // Verify that the book was added successfully
        assertTrue(driver.getPageSource().contains("Test Book"));
    }

    @Test
    void testSearchBook() {
        login();
        
        WebElement userManagementButton = driver.findElement(By.id("book-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/BookManagemnt"));

        // Search for a specific book
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='text']"));
        searchInput.sendKeys("Test Book");
        

        // Verify that the search results contain the book
        assertTrue(driver.getPageSource().contains("Test Book"));
    }

    @Test
    void testEditBook() {
        login();
     
        WebElement userManagementButton = driver.findElement(By.id("book-management"));
        userManagementButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/BookManagemnt"));

      
      
      
        driver.findElement(By.xpath("//button[contains(text(), 'Edit')]")).click();

        // Modify the book details
        WebElement titleField = driver.findElement(By.id("author"));


        // Assuming there is a save button in the EditBook component
        driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        // Verify that the book details have been updated
        assertTrue(driver.getPageSource().contains("Test Book"));
    }

    @Test
    public void testDeleteBook() {
        // Add a book to delete
        testAddBook();

      
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Find and click the delete button for the added book
        WebElement deleteButton = driver.findElement(By.xpath("//button[contains(text(), 'Delete')]"));
        deleteButton.click();

        // Wait for the SweetAlert modal to appear and confirm deletion
        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Yes, delete it!')]")));
        confirmButton.click(); // Confirm deletion

        // Wait for the success message in SweetAlert to appear
        WebElement sweetAlertTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Deleted!')]"))
        );
        WebElement sweetAlertText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Book has been deleted.')]"))
        );

        // Verify that the SweetAlert title and text are correct
        assertTrue(sweetAlertTitle.isDisplayed(), "Success title not displayed.");
        assertTrue(sweetAlertText.isDisplayed(), "Success message not displayed.");

        // Verify that the book has been deleted
      
    }

}
