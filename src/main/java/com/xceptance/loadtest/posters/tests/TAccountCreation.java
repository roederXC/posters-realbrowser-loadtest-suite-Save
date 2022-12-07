package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.FlowStoppedException;
import com.xceptance.loadtest.posters.pages.CreateAccountPage;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;


public class TAccountCreation extends LoadTestCase
{
	/**
	 * Open the landing page, browse the catalog. If there's a result grid open a random product's detail view.
	 * @throws FlowStoppedException 
	 */
    public void test() throws FlowStoppedException
    {
        // Flag test case
        Context.requiresRegisteredAccount(true);
        
        Context.get().data.attachAccount();
        
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
    	// Register new user
    	GeneralPage.openCreateNewAccount();
    	CreateAccountPage.fillAccountForm();
    	CreateAccountPage.submitForm();        	        
    }
}
