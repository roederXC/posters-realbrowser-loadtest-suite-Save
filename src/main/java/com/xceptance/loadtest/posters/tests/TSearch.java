package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.data.Tuple;
import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.posters.data.FileDataSupplier;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;

public class TSearch extends LoadTestCase
{
	/**
	 * Just execute a search and check the result as well as see a 
	 * product. It uses a test data file. Then page the result page and open a random product.
	 */
    public void test()
    {
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
    	// search, get some data first, feel free to replace the Tuple approach for the return 
    	// value if this seems to fancy or stubborn or is not needed
    	Tuple<String,String> data = FileDataSupplier.searchPhraseWithResult();
    	GeneralPage.search(data.valueA, data.valueB);
    	
    	// paging on Product Listing Pages - find "browsing.flow.paging.flow.range" in project.properties
        final int pagingRounds = Context.configuration().browsePagingFlow.random();

        for (int j = 0; j < pagingRounds; j++)
        {
            GeneralPage.executePaging();
        }        
        
        // open a Product Detail page
        GeneralPage.openProductDetailsPage();
    }
}
