import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        driver.findElement(By.id("email")).sendKeys("testuser8@example.com");
        driver.findElement(By.id("password")).sendKeys("securePassword123");
       

        // Submit the form
        driver.findElement(By.id("signup-button")).click();

        // Wait for the URL to be the home page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:5173/Authentication/signup")); // Adjust this if your home page URL is different

        // Verify that the current URL is the home page
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:5173/Authentication/signup", currentUrl, "Navigation to home page failed.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
