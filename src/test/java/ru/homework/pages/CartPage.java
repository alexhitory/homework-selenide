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
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public final class CartPage implements LoadableComponent<CartPage> {
    private final SelenideElement title = $("[data-test='title']");
    private final SelenideElement cartList = $("[data-test='cart-list']");
    private final ElementsCollection items = cartList.$$("[data-test='inventory-item']");
    private final ElementsCollection productNames = cartList.$$("[data-test='inventory-item-name']");
    private final ShoppingCart cart = new ShoppingCart();

    @Override
    public CartPage shouldBeOpen() {
        webdriver().shouldHave(urlContaining("/cart.html"));
        title.shouldBe(visible).shouldHave(exactText("Your Cart"));
        cartList.shouldBe(visible);
        return this;
    }

    public CartPage shouldContainProduct(Product product) {
        product(product).shouldMatch(product).shouldHaveQuantity(1);
        return this;
    }

    public CartPage shouldHaveProductCount(int expectedCount) {
        items.shouldHave(size(expectedCount));
        cart.shouldHaveCount(expectedCount);
        return this;
    }

    public CartPage reload() {
        refresh();
        return shouldBeOpen();
    }

    public CartPage removeProduct(Product product) {
        product(product).remove();
        productNames.filterBy(exactText(product.name())).shouldHave(size(0));
        return this;
    }

    public CartPage shouldBeEmpty() {
        return shouldHaveProductCount(0);
    }

    private ProductItem product(Product product) {
        return new ProductItem(productNames.findBy(exactText(product.name()))
                .closest("[data-test=inventory-item]"));
    }
}
