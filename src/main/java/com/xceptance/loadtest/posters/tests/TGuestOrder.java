package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.FlowStoppedException;
import com.xceptance.loadtest.api.util.SafetyBreak;
import com.xceptance.loadtest.posters.data.FileDataSupplier;
import com.xceptance.loadtest.posters.pages.CartPage;
import com.xceptance.loadtest.posters.pages.CheckoutPage;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;
import com.xceptance.loadtest.posters.pages.ProductDetailsPage;

public class TGuestOrder extends LoadTestCase
{
	/**
	 * Open the landing page, browse or search the catalog. If there's a result grid open a random product's detail view,
     * configure it and put it in the cart. Go to the checkout, enter shipping and billing and place the order.
	 * @throws FlowStoppedException 
	 */
    public void test() throws FlowStoppedException
    {
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
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
    	
    	Context.get().data.attachAccount();
    	
    	CheckoutPage.fillShippingForm();
        CheckoutPage.submitShipping();
        CheckoutPage.fillPaymentForm();
        CheckoutPage.submitBilling();
    	CheckoutPage.placeOrder();    	
    }

    private void searchOrBrowse()
    {
        if (Context.configuration().searchOnAddToCartProbability.random())
        {
            // search, get some data first, feel free to replace the Tuple approach for the return 
            // value if this seems to fancy or stubborn or is not needed
            var data = FileDataSupplier.searchPhraseWithResult();
            GeneralPage.search(data.valueA, data.valueB);            
        }
        else
        {
            // get us a category context
            final int categoryRounds = Context.configuration().browseCategoriesRounds.random();

            for (int j = 0; j < categoryRounds; j++)
            {
                // work on categories
                if(Context.get().configuration.topCategoryProbability.random())
                {
                    GeneralPage.openTopCategory();
                }
                else
                {
                    GeneralPage.openSubCategory();
                }
            }
        }
        
        // paging on Product Listing Pages - find "browsing.flow.paging.flow.range" in project.properties
        final int pagingRounds = Context.configuration().pagingRounds.random();

        for (int j = 0; j < pagingRounds; j++)
        {
            GeneralPage.executePaging();
        }        
        
        // open a Product Detail page
        GeneralPage.openProductDetailsPage();
    }
}
