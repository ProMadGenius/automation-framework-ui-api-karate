package core.ui;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import ui.PageGenerator;
import utilityClasses.GenericUtilities;
import utilityClasses.ProjectFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

import static core.ConfigReader.APP_URL;
import static core.ConfigReader.DRIVER_PATH;
import static core.ConfigReader.PIPELINE_PATH;
import static core.ui.ParallelDriver.getDriver;
import static core.ui.ParallelDriver.setDriver;
import static core.ui.WebDriverFactory.*;
import static core.ui.WebDriverFactory.initWebDriver;

/***
 * @author Isaac Zarzuri
 */

public class BaseTest {

    protected PageGenerator Navigator;
    protected SoftAssert softAssert;

    @BeforeMethod
    @Parameters({"browserName", "isHeadless"})
    public void initializeTest(String browserName, String isHeadless){
        String driverPath = setDriverBinary(browserName);
        WebDriverType type = setBrowserType(browserName);
        setDriver(startDriver(type, driverPath, isHeadless));
        Navigator = new PageGenerator();
        softAssert = new SoftAssert();
        getDriver().get(APP_URL);
    }

    private WebDriver startDriver(WebDriverType type, String driverPath, String isHeadless){
        WebDriver driver = null;
        try{
            driver = initWebDriver(type, driverPath, isHeadless);
        }
        catch (WebDriverException ex){
            Assert.fail(String.format("Unable to start the WebDriver. Error: %s", ex.getMessage()));
        }
        return driver;
    }

    private WebDriverType setBrowserType(String browserName){
        switch (browserName){
            case "chrome":
                return WebDriverType.Chrome;
            case "firefox":
                return WebDriverType.FireFox;
            default:
                throw new InputMismatchException("Selected browser is not yet supported.");
        }
    }

    private String setDriverBinary(String browserName){
        switch (browserName){
            case "chrome":
                if (GenericUtilities.getOS().equals(GenericUtilities.OS.LINUX))
                    return String.format("%s/chromedriver", PIPELINE_PATH);
                else
                    return String.format("%s/chromedriver.exe", DRIVER_PATH);
            case "firefox":
                if (GenericUtilities.getOS().equals(GenericUtilities.OS.LINUX))
                    return String.format("%s/geckdriver", PIPELINE_PATH);
                else
                    return String.format("%s/chromedriver.exe", DRIVER_PATH);
            default:
                throw new InputMismatchException("No driver binary found for the selected driver.");
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        // If the test failed - capture a screenshot
        if (ITestResult.FAILURE == result.getStatus()) {
            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
            File screenshotAsFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            // Calculate screenshot directory and file name to save as 'png' image
            String screenShotDirectoryPath = ProjectFileUtils.getProjectScreenshotDir();
            ProjectFileUtils.createDirectoryIfNotExist(screenShotDirectoryPath);
            String screenshotFileName = String.format("/%s_%s.png", result.getTestClass().getName(), result.getName());
            String screenShotFilePath = screenShotDirectoryPath + screenshotFileName;

            // Save the screenshot
            try {
                FileUtils.copyFile(screenshotAsFile, new File(screenShotFilePath));
            } catch(IOException e){
                throw new IllegalStateException(e);
            }
        }

        getDriver().quit();
    }
}
