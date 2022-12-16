package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition .*;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.data.Address;
import com.xceptance.loadtest.api.data.CreditCard;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;

/**
 * 
 * @author roederXC (Xceptance Software Technologies GmbH)
 */
public class CheckoutPage 
{
    public static void validateShippingPage()
    {
        $("#titleDelAddr").should(exist);
        $("#formAddDelAddr").should(exist);
        $("#btnAddDelAddr").should(exist);
    }
    
    public static void validatePaymentPage()
    {
        $("#titlePayment").should(exist);
        $("#formAddPayment").should(exist);
        $("#btnAddPayment").should(exist);
    }
    
    public static void validateOrderReviewPage()
    {
        $("#titleOrderOverview").should(exist);
        $("#checkoutOverviewTable").should(exist);
        $("#btnOrder").should(exist);
    }
    
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
        });       
    }
    
    public static void submitShipping()
    {
        Action.run("Shipping", () ->
        {
            $("#btnAddDelAddr").should(visible).click();
        });
        
        validatePaymentPage();
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
        });       
    }
    
    public static void submitBilling()
    {
        Action.run("SubmitShipping", () ->
        {
            $("#btnAddPayment").should(visible).click();
        });
        
        validateOrderReviewPage();
    }
    
    public static void placeOrder()
    {
        Action.run("PlaceOrder", () ->
        {       
            $("#btnOrder").scrollIntoView(false).should(visible).click();
        });       
        
        // validate that order has been placed successfully
        $("#successMessage").should(visible).shouldHave(text("Thank you"));
        Homepage.validate();
    }
}