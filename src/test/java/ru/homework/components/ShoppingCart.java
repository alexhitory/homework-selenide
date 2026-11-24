package ru.homework.components;

import com.codeborne.selenide.SelenideElement;
import ru.homework.pages.CartPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/** Shared header component, used by the inventory and cart pages. */
public final class ShoppingCart {
    private final SelenideElement link = $("[data-test='shopping-cart-link']");
    private final SelenideElement badge = link.$("[data-test='shopping-cart-badge']");

    public ShoppingCart shouldHaveCount(int count) {
        if (count == 0) {
            badge.shouldNot(exist);
        } else {
            badge.shouldBe(visible).shouldHave(exactText(String.valueOf(count)));
        }
        return this;
    }

    public CartPage open() {
        link.shouldBe(visible).click();
        return new CartPage().shouldBeOpen();
    }
}
