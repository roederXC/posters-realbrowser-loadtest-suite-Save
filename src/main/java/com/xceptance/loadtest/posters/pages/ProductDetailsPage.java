package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import org.junit.Assert;
import static com.codeborne.selenide.Condition .*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.loadtest.api.util.RandomUtils;

/**
 * 
 * @author roederXC
 */
public class ProductDetailsPage 
{
    public static void validate()
    {
        $("#addToCartForm").should(exist);
        $("#titleProductName").should(exist);
    }
    
	public static void addToCart()
	{
	    Action.run("AddToCart", () ->
	    {
	        int previousQty = Integer.valueOf($(".headerCartProductCount").should(visible).getText());
	        $("#btnAddToCart").should(exist).should(visible).should(enabled).click();
	        
	        // validate
	        $(".cartMini").should(visible);
	        int currentQty = Integer.valueOf($(".headerCartProductCount").should(visible).getText());	        
	        Assert.assertTrue("Cart quantity did not increased", currentQty > previousQty);
	        Context.get().data.totalAddToCartCount++;
	    });
	}
	
	public static void configureProductSize()
	{
	    ElementsCollection sizeOtions = $$("#selectSize > option").filter(visible);
	    if(!sizeOtions.isEmpty())
	    {
	        Action.run("ConfigureProductSize", () ->
	        {
	            final SelenideElement randomOption = RandomUtils.randomEntry(sizeOtions);
	            randomOption.click();
	        });
	    }
	}	
	
	public static void configureProductStyle()
	{
	    ElementsCollection styleOtions = $$("#selectStyle input:not([checked])").filter(visible);
	    
	    if(!styleOtions.isEmpty())
	    {
	        Action.run("ConfigureProductStyle", () ->
	        {
	            final SelenideElement randomOption = RandomUtils.randomEntry(styleOtions);
	            randomOption.click();	                
	        });
	    }
	}		
}
