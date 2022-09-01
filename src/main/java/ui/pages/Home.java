package ui.pages;

import core.ui.BasePage;
import core.ui.BaseTest;
import core.ui.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utilityClasses.GenericUtilities.handleAlert;

public class Home extends BasePage {
    @FindBy(id = "tbodyid")
    private WebElement productsTable;

    @FindBy(className = "btn-success")
    private WebElement addToCarBtn;

    @FindBy(css = "button[data-target='#orderModal']")
    private WebElement placeOrderBtn;

    @FindBy(css = "ul li a[href='index.html']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[contains(text(), 'Cart')]")
    private WebElement cartLink;

    @FindBy(xpath = "//button[contains(text(), 'Purchase')]")
    private WebElement purchaseBtn;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "country")
    private WebElement countryInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "card")
    private WebElement cardInput;

    @FindBy(id = "month")
    private WebElement monthInput;

    @FindBy(id = "year")
    private WebElement yearInput;

    @FindBy(css = "div.sweet-alert h2")
    private WebElement successMessage;

    @FindBy(className = "confirm")
    private WebElement okCompletedBtn;

    public void clickOkBtn(){
        okCompletedBtn.click();
    }

    public boolean isMessageDisplayed(String message){
        waitForElementToBeAvailable(successMessage);
        return successMessage.getText().contains(message);
    }

    public void clickPurchaseBtn(){
        purchaseBtn.click();
    }

    public void fillOrderInfo(String name, String country, String city, String card, String month, String year){
        ElementActions.setText(nameInput, name);
        ElementActions.setText(countryInput, country);
        ElementActions.setText(cityInput, city);
        ElementActions.setText(cardInput, card);
        ElementActions.setText(monthInput, month);
        ElementActions.setText(yearInput, year);
    }

    public void clickAddToCarBtn(){
        waitForElementToBeAvailable(addToCarBtn);
        addToCarBtn.click();
        handleAlert(true);
    }

    public void clickPlaceOrderBtn(){
        waitForElementToBeAvailable(placeOrderBtn);
        placeOrderBtn.click();
    }

    public void clickHomeLink(){
        homeLink.click();
    }

    public void clickCartLink(){
        cartLink.click();
    }


    public void addToCart(String productName){
        String productSelector = String.format("//a[contains(text(),'%s')]", productName);
        waitForLocatedElementToBeVisible(productsTable.findElement(By.xpath(productSelector)));
        productsTable.findElement(By.xpath(productSelector)).click();
    }
}
