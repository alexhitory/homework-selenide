package ru.homework.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.homework.components.ProductItem;
import ru.homework.components.ShoppingCart;
import ru.homework.model.Product;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public final class InventoryPage implements LoadableComponent<InventoryPage> {
    private final SelenideElement title = $("[data-test='title']");
    private final ElementsCollection products = $$("[data-test='inventory-item']");
    private final ShoppingCart cart = new ShoppingCart();

    @Override
    public InventoryPage shouldBeOpen() {
        webdriver().shouldHave(urlContaining("/inventory.html"));
        title.shouldBe(visible).shouldHave(exactText("Products"));
        return this;
    }

    public InventoryPage shouldHaveProducts(int expectedCount) {
        products.shouldHave(size(expectedCount));
        return this;
    }

    public InventoryPage addProduct(Product product) {
        product(product).shouldMatch(product).addToCart();
        return this;
    }

    public InventoryPage shouldHaveCartCount(int expectedCount) {
        cart.shouldHaveCount(expectedCount);
        return this;
    }

    public CartPage openCart() {
        return cart.open();
    }

    private ProductItem product(Product product) {
        return new ProductItem($$("[data-test='inventory-item-name']").findBy(exactText(product.name()))
                .closest("[data-test=inventory-item]"));
    }
}
