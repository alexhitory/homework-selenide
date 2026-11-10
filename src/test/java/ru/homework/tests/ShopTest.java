package ru.homework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UI-тесты магазина SauceDemo")
class ShopTest extends BaseUiTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String PRODUCT = "Sauce Labs Backpack";
    private static final String PRICE = "$29.99";

    @Test
    @DisplayName("Успешный вход открывает каталог с шестью товарами")
    void successfulLogin() {
        loginPage().loginAs(USERNAME, PASSWORD)
                .shouldBeOpen()
                .shouldHaveProducts(6);
    }

    @Test
    @DisplayName("Неверный пароль вызывает ошибку авторизации")
    void invalidPassword() {
        loginPage().attemptLogin(USERNAME, "incorrect_password")
                .shouldShowError("Epic sadface: Username and password do " +
                        "not match any user in this service");
    }

    @Test
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
}
