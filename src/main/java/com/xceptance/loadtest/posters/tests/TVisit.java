package com.xceptance.loadtest.posters.tests;

import com.xceptance.loadtest.api.tests.LoadTestCase;
import com.xceptance.loadtest.posters.pages.Homepage;

public class TVisit extends LoadTestCase
{
	/**
	 * Just open the homepage.
	 */
    public void test()
    {
    	// loads the homepage, data needed is taking from the properties automatically
    	// using the Context as well as the attached configuration.
    	Homepage.open();
    }
}
