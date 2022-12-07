package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;

import java.util.Optional;

import static com.codeborne.selenide.CollectionCondition .*;
import static com.codeborne.selenide.Condition .*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.Address;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.xlt.api.util.XltRandom;

/**
 * 
 * @author rschwietzke
 */
public class CheckoutPage 
{
    public static void fillShippingForm()
    {
        Action.run("Shipping", () ->
        {           
            Account account = Context.get().data.getAccount().get();
            Address address = account.shippingAddress;
            
            $("#fullName").should(visible).sendKeys(account.firstname + " " + account.lastname);
            $("#addressLine").should(visible).sendKeys(address.addressLine1);
            $("#city").should(visible).sendKeys(address.city);
            $("#state").should(visible).sendKeys(address.state);
            $("#zip").should(visible).sendKeys(address.zip);
                        
            $("#btnAddDelAddr").should(visible).click();
        });       
    }
    
    public static void fillPaymentForm()
    {
        Action.run("Billing", () ->
        {       
            Account account = Context.get().data.getAccount().get();
            CreditCard cc = account.creditCards.get(0);
            
            $("#creditCardNumber").should(visible).sendKeys(cc.number);
            $("#name").should(visible).sendKeys(account.firstname + " " + account.lastname);
            $("#expirationDateMonth").should(visible).sendKeys(cc.expirationMonth);
            $("#expirationDateYear").should(visible).sendKeys(cc.expirationYear);         
            
            $("#btnAddPayment").should(visible).click();
        });       
    }
    
    public static void placeOrder()
    {
        Action.run("PlaceOrder", () ->
        {       
            $("#btnOrder").scrollIntoView(false).should(visible).click();
        });       
    }
}
