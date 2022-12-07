package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
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
 * @author roederXC (Xceptance Software Technologies GmbH)
 */
public class LoginPage 
{
    public static void fillAccountForm()
    {
        Action.run("FillLoginForm", () ->
        {           
            Account account = Context.get().data.getAccount().get();
            
            $("#email").should(visible).sendKeys(account.email);
            $("#password").should(visible).sendKeys(account.password);
                        
            
        });       
    }
        
    public static void submitForm()
    {
        Action.run("SubmitLoginForm", () ->
        {       
            $("#btnSignIn").should(visible).click();
            
            // verify success page
            $("#successMessage").should(visible).shouldHave(text("Login successful"));
            Homepage.verify();
        });       
    }
}
