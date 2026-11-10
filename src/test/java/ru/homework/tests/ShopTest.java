package ru.homework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("UI-тесты магазина SauceDemo")
class ShopTest extends BaseUiTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String PRODUCT = "Sauce Labs Backpack";
    private static final String PRICE = "$29.99";

    @Test
    @Tag("smoke")
    @Tag("auth")
    @DisplayName("Успешный вход открывает каталог с шестью товарами")
    void successfulLogin() {
        loginPage().loginAs(USERNAME, PASSWORD)
                .shouldBeOpen()
                .shouldHaveProducts(6);
    }

    @Test
    @Tag("auth")
    @Tag("negative")
    @DisplayName("Неверный пароль вызывает ошибку авторизации")
    void invalidPassword() {
        loginPage().attemptLogin(USERNAME, "incorrect_password")
                .shouldShowError("Epic sadface: Username and password do " +
                        "not match any user in this service");
    }

    @Test
    @Tag("smoke")
    @Tag("cart")
    @DisplayName("Добавленный товар отображается в корзине с верной ценой и количеством")
    void addProductToCart() {
        loginPage().loginAs(USERNAME, PASSWORD)
                .shouldBeOpen()
                .addProduct(PRODUCT)
                .shouldHaveCartCount(1)
                .openCart()
                .shouldBeOpen()
                .shouldContainProduct(PRODUCT, PRICE);
    }

    @Test
    @Tag("cart")
    @DisplayName("Удаление единственного товара очищает корзину и счётчик")
    void removeProductFromCart() {
        loginPage().loginAs(USERNAME, PASSWORD)
                .shouldBeOpen()
                .addProduct(PRODUCT)
                .openCart()
                .shouldBeOpen()
                .shouldContainProduct(PRODUCT, PRICE)
                .removeProduct(PRODUCT)
                .shouldBeEmpty();
    }

    @Test
    @Tag("cart")
    @DisplayName("Товар остаётся в корзине после обновления страницы")
    void cartSurvivesPageReload() {
        loginPage().loginAs(USERNAME, PASSWORD)
                .shouldBeOpen()
                .addProduct(PRODUCT)
                .openCart()
                .shouldBeOpen()
                .reload()
                .shouldContainProduct(PRODUCT, PRICE);
    }
}
