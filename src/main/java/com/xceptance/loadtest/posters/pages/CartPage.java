package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition .*;
import static com.codeborne.selenide.Condition .*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.xlt.api.util.XltRandom;

/**
 * 
 * @author rschwietzke
 */
public class CartPage 
{
    public static void proceedToCheckout()
    {
        Action.run("Checkout", () ->
        {
            $("#btnStartCheckout").should(visible).click();
        });       
    }
}
