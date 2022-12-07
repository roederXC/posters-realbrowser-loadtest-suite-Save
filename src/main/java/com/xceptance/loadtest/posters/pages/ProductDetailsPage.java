package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;

import org.junit.Assert;

import static com.codeborne.selenide.CollectionCondition .*;
import static com.codeborne.selenide.Condition .*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.Context;
import com.xceptance.xlt.api.util.XltRandom;

/**
 * 
 * @author rschwietzke
 */
public class ProductDetailsPage 
{
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
	    if($("#selectSize").exists())
	    {
	        Action.run("ConfigureProductSize", () ->
	        {
	            ElementsCollection sizeOtions = $$("#selectSize > option").filter(visible);
	            
	            if(sizeOtions.size() > 1)
	            {
	                sizeOtions.get(XltRandom.nextInt(1, sizeOtions.size()-1)).click();
	            }
	        });
	    }
	}	
	
	public static void configureProductStyle()
	{
	    if($("#selectStyle input:not([checked])").exists())
	    {
	        Action.run("ConfigureProductStyle", () ->
	        {
	            ElementsCollection styleOtions = $$("#selectStyle input:not([checked])").filter(visible);
	            if(styleOtions.size() > 0)
	            {
	                styleOtions.get(XltRandom.nextInt(0, styleOtions.size()-1)).click();	                
	            }
	        });
	    }
	}	
	
}
