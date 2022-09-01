package ui;

import core.ui.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.Cart;
import ui.pages.Home;

import static core.ui.ParallelDriver.getDriver;

/***
 * @author Isaac Zarzuri
 */

public class PageGenerator extends BasePage {

    public Cart Cart() {
        return PageFactory.initElements(getDriver(), Cart.class);
    }
    public Home Home() {
        return PageFactory.initElements(getDriver(), Home.class);
    }
}
