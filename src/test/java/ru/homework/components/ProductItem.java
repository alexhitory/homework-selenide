package ru.homework.components;

import com.codeborne.selenide.SelenideElement;
import ru.homework.model.Product;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertAll;

/** Page Element / Wrapper for a product in either the inventory or the cart. */
public final class ProductItem {
    private final SelenideElement root;

    public ProductItem(SelenideElement root) {
        this.root = root;
    }

    public ProductItem addToCart() {
        root.shouldBe(visible).$("button").shouldHave(exactText("Add to cart")).click();
        root.$("button").shouldHave(exactText("Remove"));
        return this;
    }

    public ProductItem remove() {
        root.shouldBe(visible).$("button").shouldHave(exactText("Remove")).click();
        return this;
    }

    public ProductItem shouldMatch(Product product) {
        root.shouldBe(visible);
        assertAll("Название и цена товара",
                () -> root.$("[data-test='inventory-item-name']").shouldHave(exactText(product.name())),
                () -> root.$("[data-test='inventory-item-price']").shouldHave(exactText(product.displayedPrice())));
        return this;
    }

    public ProductItem shouldHaveQuantity(int quantity) {
        root.$("[data-test='item-quantity']").shouldHave(exactText(String.valueOf(quantity)));
        return this;
    }
}
