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
import static org.junit.jupiter.api.Assertions.*;

public class TransactionManageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    
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
    void testRenderTransactionManage() {
        login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
    

        
        assertTrue(driver.getPageSource().contains("Transaction"));
    }

    @Test
    void testInitialLoad() {
          login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
        
        // Wait for the transactions to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Pending Transactions')]")));
        assertNotNull(driver.findElement(By.xpath("//h2[contains(text(), 'Pending Transactions')]")));
    }

    @Test
    void testSearchTransaction() {
          login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
     
        
        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Enter Transaction ID...']"));
        searchInput.sendKeys("TR3626"); // Enter valid Transaction ID
        driver.findElement(By.xpath("//button[contains(text(), 'Search')]")).click();

        // Verify search results
       
        assertTrue(driver.getPageSource().contains("Expected Transaction ID or details")); // Adjust accordingly
    }

    @Test
    void testSearchWithNoResults() {
          login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
        

        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Enter Transaction ID...']"));
        searchInput.sendKeys("nonexistent"); // Enter a non-existent ID
        driver.findElement(By.xpath("//button[contains(text(), 'Search')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'No transactions found!')]")));
        assertTrue(driver.getPageSource().contains("No transactions found!"));
    }

    @Test
    void testRenderPendingTransactions() {
          login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Pending Transactions')]")));
        
        assertTrue(driver.getPageSource().contains("Pending Transactions"));
    }

    @Test
    void testRenderCompletedTransactions() {
          login();

        WebElement userManagementButton = driver.findElement(By.id("tansaction-nav"));
        userManagementButton.click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("http://localhost:5173/Admin/Transaction"));
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Completed Transactions')]")));
        
        assertTrue(driver.getPageSource().contains("Completed Transactions"));
    }

 

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
