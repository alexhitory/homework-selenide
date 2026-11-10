package ru.homework.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public final class InventoryPage {
    private final SelenideElement title = $("[data-test='title']");
    private final ElementsCollection products = $$("[data-test='inventory-item']");
    private final SelenideElement cartLink = $("[data-test='shopping-cart-link']");
    private final SelenideElement cartBadge = $("[data-test='shopping-cart-badge']");

    public InventoryPage shouldBeOpen() {
        webdriver().shouldHave(urlContaining("/inventory.html"));
        title.shouldBe(visible).shouldHave(exactText("Products"));
        return this;
    }

    public InventoryPage shouldHaveProducts(int expectedCount) {
        products.shouldHave(size(expectedCount));
        return this;
    }

    public InventoryPage addProduct(String productName) {
        product(productName).$("button").shouldHave(exactText("Add to cart")).click();
        return this;
    }

    public InventoryPage shouldHaveCartCount(int expectedCount) {
        cartBadge.shouldBe(visible).shouldHave(exactText(String.valueOf(expectedCount)));
        return this;
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage();
    }

    private SelenideElement product(String productName) {
        return $$("[data-test='inventory-item-name']").findBy(exactText(productName))
                .closest("[data-test=inventory-item]");
    }
}
