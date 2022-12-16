package com.xceptance.loadtest.posters.flows;


import com.xceptance.loadtest.api.flow.Flow;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.posters.data.FileDataSupplier;
import com.xceptance.loadtest.posters.pages.GeneralPage;

// The purpose of a flow is to summarize repetitive processes.

/** 
 * The SearchOrBrowseFlow decides on the basis of a probability whether a search or a browse is executed and executes it.
 * Then, based on a probability, a paging is executed and a product is opened.
 * 
 * @author roederXC (Xceptance Software Technologies GmbH)
 */
public class SearchOrBrowseFlow implements Flow
{
    @Override
    public void run()
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