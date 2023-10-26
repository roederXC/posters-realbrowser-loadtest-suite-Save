package com.xceptance.loadtest.posters.pages;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import org.junit.Assert;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.loadtest.api.util.Action;
import com.xceptance.loadtest.api.util.RandomUtils;

/**
 * Contains everything doable on most page types.
 * 
 * @author roederXC (Xceptance Software Technologies GmbH)
 */
public class GeneralPage
{
    public static void search(final String phrase, final String expectedCount)
    {
        Action.run("Search", () ->
        {
            // open search
            $("#header-search-trigger").click();

            // enter phrase
            $("#searchForm input[name='searchText']").sendKeys(phrase);

            // send search, this is our page load
            $("#btnSearch").click();
        });

        // verify count
        $("#totalProductCount").should(Condition.exactText(expectedCount));
    }

    public static void openTopCategory()
    {
        Action.run("OpenTopCategory", () ->
        {
            // click random top category link
            final ElementsCollection categoryLinks = getTopCategories();
            final SelenideElement randomCatagory = RandomUtils.randomEntry(categoryLinks);
            randomCatagory.click();
        });

        // verify category page
        $("#titleCategoryName").should(exist);
    }

    public static void openSubCategory()
    {
        Action.run("OpenCategory", () ->
        {
            // click random category link
            final ElementsCollection topCategories = getTopCategories();
            final SelenideElement randomTopCategory = RandomUtils.randomEntry(topCategories).hover();
            final ElementsCollection categoryLinks = randomTopCategory.$$("ul.dropdown-menu a").filterBy(visible)
                    .as("CategoryLinks").shouldBe(sizeGreaterThan(0));
            final SelenideElement randomCatagory = RandomUtils.randomEntry(categoryLinks);
            randomCatagory.click();
        });

        // verify category page
        $("#titleCategoryName").should(exist);
    }

    private static ElementsCollection getTopCategories()
    {
        return $$("#categoryMenu .header-menu-item").filterBy(visible).as("TopCategories").shouldBe(sizeGreaterThan(0));
    }

    public static void openProductDetailsPage()
    {
        Action.run("OpenProduct", () ->
        {
            final ElementsCollection productLinks = $$("#productOverview a").filterBy(visible).as("Products")
                    .shouldBe(sizeGreaterThan(0));
            final SelenideElement productLink = RandomUtils.randomEntry(productLinks);

            productLink.click();
        });

        // Check if we are on a ProductDetailPage
        ProductDetailsPage.validate();
    }

    public static void executePaging()
    {
        final ElementsCollection pagingLinks = $$("#pagination-bottom li:not(.active)>a").filterBy(visible);
        if (!pagingLinks.isEmpty())
        {
            // save the origin page number. We need this later for validation
            int originPage = Integer.valueOf($("#pagination-bottom li.active").getText());

            Action.run("Paging", () ->
            {
                final SelenideElement paginLink = RandomUtils.randomEntry(pagingLinks);
                paginLink.click();
            });

            // validation
            int newPage = Integer.valueOf($("#pagination-bottom li.active").getText());
            Assert.assertFalse("We did not changed the Page", originPage == newPage);
        }
    }

    public static void viewCart()
    {
        Action.run("ViewCart", () ->
        {
            $("#headerCartOverview").should(visible).hover();
            $("#miniCartMenu .btn-primary").should(visible).hover().click();
        });

        // check if we are on the cart page
        CartPage.validate();
    }

    public static void openCreateNewAccount()
    {
        Action.run("OpenCreateNewAccount", () ->
        {
            // click Create new Account

            $("#showUserMenu").should(visible).hover();
            $("#userMenu .goToRegistration").should(visible).click();
        });

        // check if we are on the create account page
        CreateAccountPage.validate();
    }
}