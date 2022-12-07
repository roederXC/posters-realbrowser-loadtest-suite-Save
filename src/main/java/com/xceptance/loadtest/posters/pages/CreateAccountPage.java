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
 * @author proeder
 */
public class CreateAccountPage 
{
    public static void fillAccountForm()
    {
        Action.run("FillAccountForm", () ->
        {           
            Account account = Context.get().data.getAccount().get();
            
            $("#lastName").should(visible).sendKeys(account.lastname);
            $("#firstName").should(visible).sendKeys(account.firstname);
            $("#eMail").should(visible).sendKeys(account.email);
            $("#password").should(visible).sendKeys(account.password);
            $("#passwordAgain").should(visible).sendKeys(account.password);
                        
            
        });       
    }
        
    public static void submitForm()
    {
        Action.run("SubmitAccountForm", () ->
        {       
            $("#btnRegister").should(visible).click();
            
            // verify success page
            $("#successMessage").should(visible);
            $("#btnSignIn").should(exist);
        });       
    }
}
