package ru.homework.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public final class LoginPage {
    private final SelenideElement usernameInput = $("[data-test='username']");
    private final SelenideElement passwordInput = $("[data-test='password']");
    private final SelenideElement loginButton = $("[data-test='login-button']");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public LoginPage open() {
        Selenide.open("/");
        loginButton.shouldBe(visible);
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        submitCredentials(username, password);
        return new InventoryPage();
    }

    public LoginPage attemptLogin(String username, String password) {
        submitCredentials(username, password);
        return this;
    }

    public LoginPage shouldShowError(String expectedMessage) {
        errorMessage.shouldBe(visible).shouldHave(exactText(expectedMessage));
        loginButton.shouldBe(visible);
        return this;
    }

    private void submitCredentials(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
    }
}
