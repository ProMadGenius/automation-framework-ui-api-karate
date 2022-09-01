package ui;

import core.ui.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Cart extends BaseTest {

    /**
     * This test case verifies the cart and purchase form
     * Test users: No needed
     */
    @Test(groups = {"smoke" ,"regression"})
    public void AddToTheCartAndCompletePurchase(){
        //1. Go to https://www.demoblaze.com
        //2. Add 2 products to the cart
        Navigator.Home().addToCart("Samsung galaxy s6");
        Navigator.Home().clickAddToCarBtn();
        Navigator.Home().clickHomeLink();
        Navigator.Home().addToCart("Nokia lumia 1520");
        Navigator.Home().clickAddToCarBtn();
        //3. Visualize the cart
        Navigator.Home().clickCartLink();
        //4. Complete the purchase form.
        Navigator.Home().clickPlaceOrderBtn();
        String name = "Juan";
        String country = "USA";
        String city = "Florida";
        String card = "123456789";
        String month = "June";
        String year = "2000";
        Navigator.Home().fillOrderInfo(name, country, city, card, month, year);
        //5. Complete the purchase.
        Navigator.Home().clickPurchaseBtn();
        Assert.assertTrue(Navigator.Home().isMessageDisplayed("Thank you for your purchase!"));
        Navigator.Home().clickOkBtn();
    }
}
