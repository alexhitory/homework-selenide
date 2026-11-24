package ru.homework.components;

import com.codeborne.selenide.SelenideElement;
import ru.homework.model.Product;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertAll;

/** Page Element / Wrapper for a product in either the inventory or the cart. */
public final class ProductItem {
    private final SelenideElement root;
    private final SelenideElement name;
    private final SelenideElement price;
    private final SelenideElement quantity;
    private final SelenideElement addButton;
    private final SelenideElement removeButton;

    public ProductItem(SelenideElement root) {
        this.root = root;
        name = root.$("[data-test='inventory-item-name']");
        price = root.$("[data-test='inventory-item-price']");
        quantity = root.$("[data-test='item-quantity']");
        addButton = root.$("[data-test^='add-to-cart-']");
        removeButton = root.$("[data-test^='remove-']");
    }

    public ProductItem addToCart() {
        root.shouldBe(visible);
        addButton.shouldBe(visible).shouldHave(exactText("Add to cart")).click();
        removeButton.shouldBe(visible).shouldHave(exactText("Remove"));
        return this;
    }

    public ProductItem remove() {
        root.shouldBe(visible);
        removeButton.shouldBe(visible).shouldHave(exactText("Remove")).click();
        return this;
    }

    public ProductItem shouldMatch(Product product) {
        root.shouldBe(visible);
        assertAll("Название и цена товара",
                () -> name.shouldHave(exactText(product.name())),
                () -> price.shouldHave(exactText(product.displayedPrice())));
        return this;
    }

    public ProductItem shouldHaveQuantity(int expectedQuantity) {
        quantity.shouldHave(exactText(String.valueOf(expectedQuantity)));
        return this;
    }
}
