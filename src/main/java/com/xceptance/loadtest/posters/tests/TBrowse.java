package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.posters.pages.GeneralPage;
import com.xceptance.loadtest.posters.pages.Homepage;

public class TBrowse extends LoadTestCase
{
	/**
	 * Open the landing page, browse the catalog. If there's a result grid open a random product's detail view.
	 */
    public void test()
    {
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    	
    	// Determine how often we want to decent from the top categories into the catalog
        final int rounds = Context.configuration().fullBrowseFlow.value;

        for (int i = 0; i < rounds; i++)
        {
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
                    GeneralPage.openSubCategory();
                }
            }
            
            final int refineRounds = Context.configuration().browseRefineFlow.value;

            for (int j = 0; j < refineRounds; j++)
            {
                GeneralPage.executePaging();
                GeneralPage.openProductDetailsPage();
            }
        }
    }
}
