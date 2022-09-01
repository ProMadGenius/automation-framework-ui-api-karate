package ui.pages;

import core.ui.BasePage;
import core.ui.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

// page_url = https://www.microsoft.com/es-mx/
public class Cart extends BasePage {
    @FindBy(id = "search")
    private WebElement searchIcon;

    @FindBy(id = "uhf-shopping-cart")
    private WebElement cartBtn;

    @FindBy(id = "shellmenu_2")
    private WebElement windowsLink;

    @FindBy(id = "cli_shellHeaderSearchInput")
    private WebElement searchInput;

    @FindBy(id = "universal-header-search-auto-suggest-ul")
    private WebElement suggestionListSearch;

    @FindBy(id = "onestore-quicklinksmodule-po1wqqs-quicklink2")
    private WebElement digitalGamesLink;

    @FindBy(id = "R1MarketRedirect-1")
    private WebElement changeStoreLanguageDialog;

    @FindBy(id = "R1MarketRedirect-close")
    private WebElement stayIntheSiteDialogBtn;

    @FindBy(css = "a[aria-label='ver colección xbox juegos digitales']")
    private WebElement digitalGamesCollectionLink;

    @FindBy(css = "div[class='c-group f-wrap-items context-list-page']")
    private WebElement groupItemGames;

    @FindBy(css = "a[aria-label='página siguiente']")
    private WebElement nextPage;

    @FindBy(css = "a[aria-label='página anterior']")
    private WebElement previousPage;

    @FindBy(xpath = "//button[contains(@data-m, 'BuyButton')]")
    private WebElement buyButton;

    @FindBy(css = ".c-channel-placement-price span[itemprop='price']")
    private WebElement priceItem;

    @FindBy(xpath = "//div[@class='ProductActionsPanel-module__desktopProductActionsPanel___1MnpC']//span[contains(@class, 'Price-module__boldText___34T2w')]")
    private WebElement priceInIndividualPage;

    @FindBy(css = ".store-cart-app p.c-paragraph-2")
    private WebElement messageInCart;

    @FindBy(className = "sfw-dialog")
    private WebElement newsDialog;

    @FindBy(css = "div.sfw-dialog div[class='c-glyph glyph-cancel']")
    private WebElement newsDialogClose;

    @FindBy(xpath = "//button[contains(@title, 'Agregar')]")
    private WebElement addToTheCart;

    public void clickCartIcon(){
        cartBtn.click();
    }

    public void clickAddToTheCart(){
        addToTheCart.click();
    }
    public boolean isNewsDialogDisplayed(){
        return newsDialog.isDisplayed();
    }

    public void clickCloseNewsDialog(){
        newsDialogClose.click();
    }

    public String getCartInfo(){
        waitForElementToBeClickable(cartBtn);
        return cartBtn.getAttribute("aria-label");
    }

    public String getMessageYourCartIsEmpty(){
        return messageInCart.getText();
    }

    public float getPriceFromNItem(int pos){
        return getPriceFromString(groupItemGames.findElements(By.cssSelector("span[itemprop='price']")).get(pos).getAttribute("content"));
    }

    public float getPriceInIndividualPage(){
        return getPriceFromString(priceInIndividualPage.getText());
    }

    public void clickBuyButton(){
        buyButton.click();
    }

    public void clickNextPage(){
        nextPage.click();
    }

    public void clickPreviousPage(){
        previousPage.click();
    }

    public int getCountOfItemDigitalGame(){
        return groupItemGames.findElements(By.cssSelector("div.m-channel-placement-item img")).size();
    }

    public void clickInTheNProduct(int n){
        groupItemGames.findElements(By.cssSelector("div.m-channel-placement-item img")).get(n).click();
    }

    public void printTitlesInThePage(){
        groupItemGames.findElements(By.cssSelector("div.m-channel-placement-item img")).forEach(webElement -> System.out.println(webElement.getAttribute("title")));
    }

    public void clickWindowsLink(){
        waitForElementToBeClickable(windowsLink);
        windowsLink.click();
        waitForElementToBeClickable(searchIcon);
    }

    public void clickStayInTheSite(){
        stayIntheSiteDialogBtn.click();
    }

    public void clickDigitalCollectionLink(){
        digitalGamesCollectionLink.click();
    }

    public boolean isChangeStoreDialogDisplayed(){
        waitForLocatedElementToBeVisible(changeStoreLanguageDialog);
        return changeStoreLanguageDialog.isDisplayed();
    }

    public void clickDigitalGamesLink(){
        waitForElementToBeClickable(digitalGamesLink);
        digitalGamesLink.click();
    }

    public void clickSearchIcon(){
        searchIcon.click();
        waitForElementToBeClickable(searchInput);
    }

    public void searchText(String text){
        searchInput.clear();
        ElementActions.setText(searchInput, text);
    }

    public void selectInSuggestList(int pos){
        waitForElementToBeAvailable(suggestionListSearch);
        List<WebElement> list = suggestionListSearch.findElements(By.className("c-menu-item"));
        list.get(pos).click();
    }

    public float getPriceFromString(String input){
        return Float.parseFloat(input.replaceAll(".*?(-?[\\d.]+).*", "$1"));
    }



}