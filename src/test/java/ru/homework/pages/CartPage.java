package ru.homework.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public final class CartPage {
    private final SelenideElement title = $("[data-test='title']");
    private final ElementsCollection items = $$("[data-test='inventory-item']");
    private final SelenideElement cartBadge = $("[data-test='shopping-cart-badge']");

    public CartPage shouldBeOpen() {
        webdriver().shouldHave(urlContaining("/cart.html"));
        title.shouldBe(visible).shouldHave(exactText("Your Cart"));
        return this;
    }

    public CartPage shouldContainProduct(String productName, String price) {
        items.shouldHave(size(1));
        SelenideElement item = product(productName);
        item.$("[data-test='inventory-item-name']").shouldHave(exactText(productName));
        item.$("[data-test='inventory-item-price']").shouldHave(exactText(price));
        item.$("[data-test='item-quantity']").shouldHave(exactText("1"));
        return this;
    }

    public CartPage removeProduct(String productName) {
        product(productName).$("button").shouldHave(exactText("Remove")).click();
        return this;
    }

    public CartPage shouldBeEmpty() {
        items.shouldHave(size(0));
        cartBadge.shouldNot(exist);
        return this;
    }

    private SelenideElement product(String productName) {
        return $$("[data-test='inventory-item-name']").findBy(exactText(productName))
                .closest("[data-test=inventory-item]");
    }
}
