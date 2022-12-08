package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition .*;
import com.xceptance.loadtest.api.data.Account;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;

/**
 * 
 * @author roederXC (Xceptance Software Technologies GmbH)
 */
public class CreateAccountPage 
{
    public static void validate()
    {
        $("#formRegister").should(exist);
        $("#btnRegister").should(exist);
    }
    
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
            
            // check if we are on the login page
            LoginPage.validate();
        });       
    }
}
