package core.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

import static core.ui.ParallelDriver.getDriver;

public class ElementActions {

    public static void selectByText(WebElement element, String optionToSelect){
        try{
            if (optionToSelect.isEmpty())
                return;
            Select s = new Select(element);
            s.selectByVisibleText(optionToSelect);
        }
        catch (NoSuchElementException e){
            Assert.fail(String.format("Unable to select element. Reason: %s", e.getMessage()));
        }
    }
    /*
    This work for table element only, returning the position
     */
    public static int getRowInATableByText(WebElement parentElement, String text){
        WebElement td = parentElement.findElement(By.xpath(String.format("//td[. = '%s']/parent::tr", text)));
        List<WebElement> children = parentElement.findElements(By.tagName("tr"));
        return children.indexOf(td);
    }

    /*
    This work for table element only, returning if a row value exist, before trying to get the position and get exception
     */
    public static boolean existValueInATableByText(WebElement parentElement, String text){
        List<WebElement> children = parentElement.findElements(By.xpath(String.format("//td[. = '%s']", text)));
        return children.size() > 0;
    }

    /*
    This will work in list of elements that are not table like: Lead Information, Insurance Information, and Device Information
     */
    public static int getRowInAListByText(WebElement parentElement, String uniqueText){
        WebElement td = parentElement.findElement(By.xpath(String.format("//input[@value='%s']/ancestor::div[@class='row rowStretch location-row' and @style='position: relative;']", uniqueText)));
        List<WebElement> children = parentElement.findElements(By.xpath("//div[@class='row rowStretch location-row' and @style='position: relative;']"));
        int index = children.indexOf(td);
        return index+1;
    }

    public static boolean optionExistsInDropDown(WebElement element, String optionToSelect){
        try{
            Select select = new Select(element);
            return select.getOptions().stream().anyMatch(o -> o.getText().equalsIgnoreCase(optionToSelect));
        }
        catch (NoSuchElementException e){
            Assert.fail(String.format("Unable to select element. Error: %s", e.getMessage()));
            return false;
        }
    }

    public static void selectByIndex(WebElement element, int indexOption){
        try{
            Select s = new Select(element);
            s.selectByIndex(indexOption);
        }
        catch (NoSuchElementException e){
            Assert.fail("Unable to click element. Element not found.");
        }
    }

    public static String getSelectedText(WebElement element){
        Select s = new Select(element);
        return s.getFirstSelectedOption().getText();
    }

    public static void setText(WebElement element, String text){
        try{
            element.clear();
            element.sendKeys(text);
        }
        catch(NoSuchElementException e){
            Assert.fail(String.format("Unable to set text. Error: %s", e.getMessage()));
        }
    }

    public static void toggle(WebElement element, boolean state){
        if ((!element.isSelected() || state) && (!element.isSelected() && state))
            return;
        element.sendKeys(Keys.SPACE);
    }

    public static WebElement getTableElement(WebElement element, int row, int column, boolean isHeader){
        WebElement tableElement;
        if (isHeader)
            tableElement = element.findElement(By.xpath(String.format("./thead/tr/th[%s]", column)));
        else
            tableElement = element.findElement(By.xpath(String.format("./tbody/tr[%s]/td[%s]", row, column)));
        return tableElement;
    }

    public static String getClassFromHeader(WebElement table, int column){
        WebElement header = getTableElement(table, 0, column, true);
        return header.getAttribute("class");
    }

    public static WebElement getTableElement(WebElement element, int row, String valueToSearch, int column, boolean isHeader){
        WebElement tableElement;
        if (isHeader)
            tableElement = element.findElement(By.xpath(String.format("./thead/tr/th[text()='%s']/../tr/th[%s]", valueToSearch, column)));
        else
            tableElement = element.findElement(By.xpath(String.format("./tbody/tr[%s]/td[text()='%s']/../tr/td[%s]", row, valueToSearch, column)));
        return tableElement;
    }

    public static WebElement getTableElement(WebElement element, int row, String valueToSearch, boolean isHeader){
        WebElement tableElement;
        if (isHeader)
            tableElement = element.findElement(By.xpath(String.format("./thead/tr/th[text()='%s']", valueToSearch)));
        else
            tableElement = element.findElement(By.xpath(String.format("./tbody/tr[%s]/td[text()='%s']", row, valueToSearch)));
        return tableElement;
    }

    public static int getRowCount(WebElement element, boolean isHeader){
        int count;
        if (isHeader){
            count = element.findElements(By.xpath("./thead/tr")).size();
        }
        else{
            count = element.findElements(By.xpath("./tbody/tr")).size();
        }
        return count;
    }

    public static void scrollIntoView(WebElement element){ ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", element); }

    public static void scrollToTop(){ ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, -document.body.scrollHeight)"); }

    public static void JSClick(WebElement element){ ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element); }

    public static String getInnerTextFromElement(WebElement element){
        return (String)((JavascriptExecutor)getDriver()).executeScript("return arguments[0].outerHTML;", element);
    }

    public static void hoverOver(WebElement element){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();
    }

    public static boolean isElementDisplayed(By element) {
        try
        {
            return getDriver().findElement(element).isDisplayed();

        }
        catch (NoSuchElementException ex)
        {
            return false;
        }
    }
    
    // overloading this method to accept a WebElement
    public static boolean isElementDisplayed(WebElement element) {
        try
        {
            return element.isDisplayed();

        }
        catch (NoSuchElementException ex)
        {
            return false;
        }
    }


    //There are some scenarios where an element is displayed but Selenium return false to isDisplayed, so this an alternative method
    public static boolean isElementPresent(By element) {
        try
        {
            return !getDriver().findElements(element).isEmpty();
        }
        catch (NoSuchElementException ex)
        {
            return false;
        }
    }

    public static boolean isElementEnabled(WebElement element) {
        return element.isEnabled();
    }
}
