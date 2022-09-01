package core.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static core.ConfigReader.APP_URL;
import static core.ui.ParallelDriver.getDriver;

/**
 * @author Isaac Zarzuri
 * This class is Base Page class. Each page class extends this BasePage class.
 */
public class BasePage {

	protected WebDriverWait wait;
	protected FluentWait fluentWait;
	private final int defaultTimeOut = 6;
	private final int defaultPollTime = 2;

	protected BasePage() {
		wait = new WebDriverWait(getDriver(),  Duration.ofSeconds(45));
		fluentWait = new FluentWait(getDriver());
	}

	void waitForElement(WebElement element){ wait.until(ExpectedConditions.visibilityOf(element)); }

	protected void waitForElementToBeClickable(WebElement element){ wait.until(ExpectedConditions.elementToBeClickable(element)); }

	protected void waitForTextInElement(WebElement element, String expectedText){ wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText)); }

	protected void waitForValueInElement(WebElement element, String expectedValue){ wait.until(ExpectedConditions.textToBePresentInElementValue(element, expectedValue)); }

	protected void waitForLocatedElementToDisappear(String locator){ wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator))); }

	protected void waitForTextInLocatedElement(String locator, String textToWaitFor){ wait.until(ExpectedConditions.textToBe(By.xpath(locator), textToWaitFor));}

	protected void waitForLocatedElementToBeVisible(By locator) { wait.until(ExpectedConditions.visibilityOfElementLocated (locator)); }

	protected void waitForLocatedElementToBeVisible(WebElement element) { wait.until(ExpectedConditions.visibilityOf(element)); }

	protected void waitForElementToDisappear(WebElement element){ wait.until(ExpectedConditions.invisibilityOf(element)); }

	protected void waitUntilLocatorIsNotDisplayed(By element) { wait.until(webDriver -> !ElementActions.isElementDisplayed(element)); }

	protected boolean waitUntilElementIsNotDisplayed(WebElement element) { wait.until(webDriver -> !ElementActions.isElementDisplayed(element));
		return false;
	}

	protected void waitForElementToBeDisplayed(WebElement element) { wait.until(webDriver -> ElementActions.isElementDisplayed(element)); }

	protected void waitForElementToBeAvailable(WebElement element){
		waitForElement(element);
		waitForElementToBeClickable(element);
	}

	protected void waitForScreenToLoad(By locator){
		waitForElementToBeAvailable(getDriver().findElement(locator));
	}

	protected void waitSelectIsFilled(WebElement parentLocator, By childLocator) {
		fluentWaitUntil(defaultTimeOut,
				defaultPollTime,
				(Function<WebDriver, Boolean>) driver -> {
					List<WebElement> allChildren = parentLocator.findElements(childLocator);
					if (!allChildren.isEmpty() && allChildren.size() > 1) {
						return true;
					}
					return false;
				});
	}

	protected void waitForElementStaleness(WebElement element) {
		wait.until(ExpectedConditions.stalenessOf(element));
	}

	/**
	 * Waits for an element is no longer attached to the DOM.
	 * A try-catch was added in case execution is so fast element can't be found and NoSuchElementException is caught.
	 * @param locator By locator to be found.
	 */
	protected void waitForElementStaleness(By locator) {
		try {
			waitForElementStaleness(getDriver().findElement(locator));
		} catch (NoSuchElementException ex) {
			//logger.trace("waitForElementStaleness exception: {}", ex.getMessage());
		}
	}

	public void initializeNewTab(){
		((JavascriptExecutor)getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(1));
		getDriver().get(APP_URL);
	}

	public void closeTab(){
		getDriver().close();
	}

	public <T,V> void fluentWaitUntil(int timeout, int pollTime, Function<T, V> function) {
		fluentWait.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollTime))
				.until(function);
	}
}
