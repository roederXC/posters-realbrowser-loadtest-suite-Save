package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.FlowStoppedException;
import com.xceptance.loadtest.api.util.SafetyBreak;
import com.xceptance.loadtest.posters.flows.SearchOrBrowseFlow;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;
import com.xceptance.loadtest.posters.pages.ProductDetailsPage;

public class TAddToCart extends LoadTestCase
{
    /**
     * Open the landing page, browse the catalog. If there's a result grid open a random product's detail view and add it to cart.
     * @throws FlowStoppedException 
     */
    @Override
    public void test() throws FlowStoppedException
    {
        // loads the homepage, data needed is taking from the properties automatically
        // using the Context as well as the attached configuration.
        Homepage.open();

        // You can set up a distribution how many Items you want in how many cases in your cart.
        // Find cart.add.count.distribution in project.properties or overwrite it in dev.properties.
        int targetItemCount = Context.configuration().addToCartCount.value;
        
        final SafetyBreak addToCartSafetyBreak = new SafetyBreak(5);
        while (Context.get().data.totalAddToCartCount < targetItemCount)
        {
            // Check if the maximum number of attempts is reached
            addToCartSafetyBreak.check("Unable to add the desired number of products to the cart.");            
            
            // do a search or a browse and open a random product
            new SearchOrBrowseFlow().run();

            // configure product
            ProductDetailsPage.configureProductSize();
            ProductDetailsPage.configureProductStyle();

            // add to cart
            ProductDetailsPage.addToCart();
        }
        GeneralPage.viewCart();
    }
}