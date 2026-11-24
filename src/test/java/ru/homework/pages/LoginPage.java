package ru.homework.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.homework.model.Credentials;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public final class LoginPage implements LoadableComponent<LoginPage> {
    private final SelenideElement usernameInput = $("[data-test='username']");
    private final SelenideElement passwordInput = $("[data-test='password']");
    private final SelenideElement loginButton = $("[data-test='login-button']");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public LoginPage open() {
        Selenide.open("/");
        return shouldBeOpen();
    }

    @Override
    public LoginPage shouldBeOpen() {
        usernameInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
        loginButton.shouldBe(visible);
        return this;
    }

    public InventoryPage loginAs(Credentials credentials) {
        submitCredentials(credentials);
        return new InventoryPage().shouldBeOpen();
    }

    public LoginPage attemptLogin(Credentials credentials) {
        submitCredentials(credentials);
        return this;
    }

    public LoginPage shouldShowError(String expectedMessage) {
        errorMessage.shouldBe(visible).shouldHave(exactText(expectedMessage));
        loginButton.shouldBe(visible);
        return this;
    }

    private void submitCredentials(Credentials credentials) {
        usernameInput.setValue(credentials.username());
        passwordInput.setValue(credentials.password());
        loginButton.click();
    }
}
