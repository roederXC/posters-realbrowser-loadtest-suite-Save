package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.xceptance.loadtest.api.util.Action;

/**
 * 
 * @author rschwietzke
 */
public class CartPage
{
    public static void validate()
    {
        $("#titleCart").should(exist);
    }

    public static void proceedToCheckout()
    {
        Action.run("Checkout", () ->
        {
            $("#btnStartCheckout").should(visible).click();

            // check if we are on the checkout page
            CheckoutPage.validateShippingPage();
        });
    }
}