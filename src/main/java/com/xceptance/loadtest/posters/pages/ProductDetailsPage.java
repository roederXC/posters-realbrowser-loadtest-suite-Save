package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
	    // get the current quantity. We need this later for validation
	    int previousQty = Integer.valueOf($(".headerCartProductCount").should(visible).getText());

	    Action.run("AddToCart", () ->
	    {
	        $("#btnAddToCart").should(exist).should(visible).should(enabled).click();
	    });
	    
	    // validate
	    $(".cartMini").should(visible);
	    int currentQty = Integer.valueOf($(".headerCartProductCount").should(visible).getText());	        
	    Assert.assertTrue("Cart quantity did not increased", currentQty > previousQty);
	    Context.get().data.totalAddToCartCount++;
	}
	
	public static void configureProductSize()
	{
	    // get the select element
	    SelenideElement selectElement = $("#selectSize").should(visible);
	    
	    //convert it to Select and get all options
	    Select select = new Select(selectElement);
	    List<WebElement> options = select.getOptions();
	    
	    // get the current price. We need this later for validation
	    String oldPrice = $("#prodPrice").getText();
	    
	    // remove all selected Options
	    options.removeIf(o -> o.isSelected());	    
	    
	    // click one of the not selected options
	    if(!options.isEmpty())
	    {
	        Action.run("ConfigureProductSize", () ->
	        {
	            final WebElement randomOption = RandomUtils.randomEntry(options);
	            randomOption.click();
	        });
	        
	        // validate that the price has changed
	        String newPrice = $("#prodPrice").getText();
	        assertTrue(oldPrice != newPrice);
	    }
	}	
	
	public static void configureProductStyle()
	{ 
	    // get all unselected styles
	    ElementsCollection styleOptions = $$("#selectStyle input:not([checked])").filter(visible);
	    	    
	    if(!styleOptions.isEmpty())
	    {
	        Action.run("ConfigureProductStyle", () ->
	        {	            
	            final SelenideElement randomOption = RandomUtils.randomEntry(styleOptions);
	            randomOption.click();
	        });
	    }
	}		
}
