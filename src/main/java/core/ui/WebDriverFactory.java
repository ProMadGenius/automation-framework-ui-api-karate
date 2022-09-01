package core.ui;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

	public static WebDriver initWebDriver(WebDriverType type, String driverPath, String isHeadless){
		if(Objects.nonNull(driverPath)){
			System.setProperty(type.driverName(), driverPath);
		}
		WebDriver driver;
		switch (type){
			case Chrome:
				driver = initChromeDriver(isHeadless);
				break;
			case FireFox:
				driver = initFirefoxDriver(isHeadless);
				break;
			default:
				throw new WebDriverException("Invalid driver selected.");
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

	private static WebDriver initChromeDriver(String isHeadless){
		WebDriver driver = null;
		String downloadFilepath = System.getProperty("user.dir") + "/target/downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.setBinary("C:/Program Files/Google/Chrome/Application/chrome.exe");

		if (isHeadless.equalsIgnoreCase("true")) {
			options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		}
		driver = new ChromeDriver(options);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String isHeadless){
		WebDriver driver = null;
		FirefoxProfile profile = new FirefoxProfile();

		// Instructing firefox to use custom download location
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", System.getProperty("user.dir") + "/target/downloads");
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
		profile.setPreference("browser.helperApps.neverAsk.openFile", "application/pdf, application/octet-stream, application/x-winzip, application/x-pdf, application/x-gzip");

		// Skipping Save As dialog box for types of files with their MIME
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");

		// Creating FirefoxOptions to set profile
		FirefoxOptions options =  new FirefoxOptions();
		if (isHeadless.equalsIgnoreCase("true")){
			options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080","--ignore-certificate-errors");
		}
		options.setProfile(profile);
		driver = new FirefoxDriver(options);
		return driver;
	}

	public enum WebDriverType{
		Chrome("webdriver.chrome.driver"),
		FireFox("webdriver.firefox.driver"),
		Safari("webdriver.safari.driver");

		private String driverInfo;

		WebDriverType(String driverInfo){ this.driverInfo = driverInfo; }

		public String driverName() { return this.driverInfo; }
	}

}
