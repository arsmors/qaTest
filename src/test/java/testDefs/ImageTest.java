package testDefs;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;

public class ImageTest {
    WebDriver driver;

    @When("^I navigate to google homepage$")
    public void iNavigateToGoogleHomepage() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://www.google.com");

        WebElement googleLogo = driver.findElement(By.id("hplogo"));
        File screenshot = googleLogo.getScreenshotAs(OutputType.FILE);
        File file = new File("./screenshots/Actual.png");
        if (file.exists()) {
            file.delete();
        }
        Files.copy(screenshot.toPath(), file.toPath());
        driver.quit();
    }

    @Then("^I verify that main image matches test image$")
    public void iVerifyThatMainImageMatchesTestImage() throws Throwable {
        BufferedImage expectedImage = ImageIO.read(new File("./screenshots/test.jpg"));

        BufferedImage actualImage = ImageIO.read(new File("./screenshots/Actual.png"));

        ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);

        float percent = 0.80f;
        int imgSize = actualImage.getWidth() * actualImage.getHeight();
        int proximity = Math.round(imgSize - (imgSize * percent));

        assertFalse("Images are not the same", diff.withDiffSizeTrigger(proximity).hasDiff());
    }
}


