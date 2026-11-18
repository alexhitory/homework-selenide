package ru.homework.tests;

import org.junit.jupiter.api.DisplayName;
import ru.homework.model.Credentials;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static ru.homework.data.TestData.*;

@DisplayName("UI-тесты магазина SauceDemo")
class ShopTest extends BaseUiTest {

    @Test
    @Tag("cart")
    @DisplayName("Два разных товара отображаются в корзине с верными ценами и количеством")
    void addTwoProductsToCart() {
        loginPage().loginAs(STANDARD_USER)
                .addProduct(BACKPACK)
                .addProduct(BIKE_LIGHT)
                .shouldHaveCartCount(2)
                .openCart()
                .shouldHaveProductCount(2)
                .shouldContainProduct(BACKPACK)
                .shouldContainProduct(BIKE_LIGHT);
    }

    @Test
    @Tag("cart")
    @DisplayName("Удаление одного из двух товаров сохраняет второй и обновляет счётчик")
    void removeOneOfTwoProducts() {
        loginPage().loginAs(STANDARD_USER)
                .addProduct(BACKPACK)
                .addProduct(BIKE_LIGHT)
                .openCart()
                .shouldHaveProductCount(2)
                .removeProduct(BACKPACK)
                .shouldHaveProductCount(1)
                .shouldContainProduct(BIKE_LIGHT);
    }

    @Test
    @Tag("smoke")
    @Tag("auth")
    @DisplayName("Успешный вход открывает каталог с шестью товарами")
    void successfulLogin() {
        loginPage().loginAs(STANDARD_USER)
                .shouldHaveProducts(6);
    }

    @Test
    @Tag("auth")
    @Tag("negative")
    @DisplayName("Неверный пароль вызывает ошибку авторизации")
    void invalidPassword() {
        loginPage().attemptLogin(new Credentials(STANDARD_USER.username(), "incorrect_password"))
                .shouldShowError("Epic sadface: Username and password do " +
                        "not match any user in this service");
    }

    @Test
    @Tag("smoke")
    @Tag("cart")
    @DisplayName("Добавленный товар отображается в корзине с верной ценой и количеством")
    void addProductToCart() {
        loginPage().loginAs(STANDARD_USER)
                .addProduct(BACKPACK)
                .shouldHaveCartCount(1)
                .openCart()
                .shouldHaveProductCount(1)
                .shouldContainProduct(BACKPACK);
    }

    @Test
    @Tag("cart")
    @DisplayName("Удаление единственного товара очищает корзину и счётчик")
    void removeProductFromCart() {
        loginPage().loginAs(STANDARD_USER)
                .addProduct(BACKPACK)
                .openCart()
                .shouldHaveProductCount(1)
                .shouldContainProduct(BACKPACK)
                .removeProduct(BACKPACK)
                .shouldBeEmpty();
    }

    @Test
    @Tag("cart")
    @DisplayName("Товар остаётся в корзине после обновления страницы")
    void cartSurvivesPageReload() {
        loginPage().loginAs(STANDARD_USER)
                .addProduct(BACKPACK)
                .openCart()
                .reload()
                .shouldHaveProductCount(1)
                .shouldContainProduct(BACKPACK);
    }
}
