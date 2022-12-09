package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.RandomUtils;
import com.xceptance.xlt.api.util.XltRandom;

/**
 * Contains everything doable on all page types.
 * 
 * @author rschwietzke
 */
public class GeneralPage 
{
	public static void search(final String phrase, final String expectedCount)
	{
		Action.run("Search", () ->
		{
			// enter phrase
			$("#search-form input[name='searchText']").sendKeys(phrase);

            // send search, this is our page load
            $("#btnSearch").click();

            // verify count
            $("#totalProductCount").should(Condition.exactText(expectedCount));
        });
    }

    public static void openTopCategory()
    {
        Action.run("OpenTopCategory", () ->
        {
            // click random top category link
            final ElementsCollection categoryLinks = getTopCategories();
            final SelenideElement randomCatagory = categoryLinks.get(XltRandom.nextInt(0, categoryLinks.size()-1));
            randomCatagory.click();

            // verify category page
            $("#titleCategoryName").should(exist);
        });
    }

    public static void openSubCategory()
    {
        Action.run("OpenCategory", () ->
        {
            // click random category link
            final ElementsCollection topCategories = getTopCategories();
            final SelenideElement randomTopCategory = topCategories.get(XltRandom.nextInt(0, topCategories.size()-1)).hover();
            final ElementsCollection categoryLinks = randomTopCategory.$$("ul.dropdown-menu a").filterBy(visible).as("CategoryLinks").shouldBe(sizeGreaterThan(0));
            final SelenideElement randomCatagory = categoryLinks.get(XltRandom.nextInt(0, categoryLinks.size()-1));
            randomCatagory.click();

            // verify category page
            $("#titleCategoryName").should(exist);
        });
    }

    private static ElementsCollection getTopCategories()
    {
        return $$("#categoryMenu .header-menu-item").filterBy(visible).as("TopCategories").shouldBe(sizeGreaterThan(0));
    }

    public static void openProductDetailsPage()
    {
        Action.run("OpenProduct", () ->
        {
            final ElementsCollection productLinks = $$("#productOverview a").filterBy(visible).as("Products").shouldBe(sizeGreaterThan(0));
            final SelenideElement productLink = RandomUtils.randomEntry(productLinks);

            productLink.click();

            // Check if we are on a ProductDetailPage
            ProductDetailsPage.validate();
        });
    }

    public static void executePaging()
    {
        final ElementsCollection pagingLinks = $$("#pagination-bottom li:not(.active)>a").filterBy(visible);
        if(!pagingLinks.isEmpty())
        {
            Action.run("Paging", () ->
            {
                final SelenideElement paginLink = RandomUtils.randomEntry(pagingLinks);
                paginLink.click();

                //TODO post validation?
            });           
        }       
    }

    public static void viewCart()
    {
        Action.run("ViewCart", () ->
        {
            $("#headerCartOverview").should(visible).hover();
            $("#miniCartMenu .btn-primary").should(visible).hover().click();

            // check if we are on the cart page
            CartPage.validate();
        });        
    }

    public static void openCreateNewAccount()
    {
        Action.run("OpenCreateNewAccount", () ->
        {
            // click Create new Account

            $("#showUserMenu").should(visible).hover();
            $("#userMenu .goToRegistration").should(visible).click();

            // check if we are on the create account page
            CreateAccountPage.validate();
        });
    }
}
