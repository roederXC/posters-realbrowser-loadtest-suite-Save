package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.FlowStoppedException;
import com.xceptance.loadtest.api.util.SafetyBreak;
import com.xceptance.loadtest.posters.data.FileDataSupplier;
import com.xceptance.loadtest.posters.pages.CartPage;
import com.xceptance.loadtest.posters.pages.CheckoutPage;
import com.xceptance.loadtest.posters.pages.CreateAccountPage;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;
import com.xceptance.loadtest.posters.pages.LoginPage;
import com.xceptance.loadtest.posters.pages.ProductDetailsPage;
import com.xceptance.loadtest.posters.pages.SearchResultPage;


public class TRegisteredCheckout extends LoadTestCase
{
	/**
	 * Open the landing page, browse the catalog. If there's a result grid open a random product's detail view.
	 * @throws FlowStoppedException 
	 */
    public void test() throws FlowStoppedException
    {
        Context.get().data.attachAccount();
        
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
    	// Register new user
    	GeneralPage.openCreateNewAccount();
    	CreateAccountPage.fillAccountForm();
    	CreateAccountPage.submitForm();    
    	
    	// Login
    	LoginPage.fillAccountForm();
    	LoginPage.submitForm();
    	
    	
    	int targetItemCount = Context.configuration().addToCartCount.value;
    	final SafetyBreak addToCartSafetyBreak = new SafetyBreak(5);
    	while (Context.get().data.totalAddToCartCount < targetItemCount)
        {
            // Check if the maximum number of attempts is reached
            addToCartSafetyBreak.check("Unable to add the desired number of products to the cart.");
            searchOrBrowse();
            
            // configure product
            ProductDetailsPage.configureProductSize();
            ProductDetailsPage.configureProductStyle();
            
            // add to cart
            ProductDetailsPage.addToCart();
        }
    	GeneralPage.viewCart();
    	
    	CartPage.proceedToCheckout();
    	
    	CheckoutPage.fillShippingForm();
    	CheckoutPage.fillPaymentForm();  	
    }

    private void searchOrBrowse()
    {
        if (Context.configuration().searchOnAddToCartProbability.random())
        {
            // search, get some data first, feel free to replace the Tuple approach for the return 
            // value if this seems to fancy or stubborn or is not needed
            var data = FileDataSupplier.searchPhraseWithResult();
            GeneralPage.search(data.valueA, data.valueB);
            
            // view a product, this logic here expects that we have a product!
            SearchResultPage.pdp();
        }
        else
        {
            // get us a category context
            final int categoryRounds = Context.configuration().browseCategoriesFlow.value;

            for (int j = 0; j < categoryRounds; j++)
            {
                // work on categories
                if(Context.get().configuration.topCategoryBrowsing.random())
                {
                    GeneralPage.openTopCategory();
                }
                else
                {
                    GeneralPage.openCategory();
                }
            }
        }
        
        // browse the page
        final int refineRounds = Context.configuration().browseRefineFlow.value;

        for (int j = 0; j < refineRounds; j++)
        {
            GeneralPage.executePaging();
            GeneralPage.openProductDetailsPage();
        }
        
    }
}
