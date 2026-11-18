package ru.homework.data;

import ru.homework.model.Credentials;
import ru.homework.model.Product;

import java.math.BigDecimal;
import java.util.stream.Stream;

/** Shared immutable data registry and a provider of negative login cases. */
public final class TestData {
    public static final Credentials STANDARD_USER = new Credentials("standard_user", "secret_sauce");
    public static final Product BACKPACK = new Product("Sauce Labs Backpack", new BigDecimal("29.99"));
    public static final Product BIKE_LIGHT = new Product("Sauce Labs Bike Light", new BigDecimal("9.99"));

    private TestData() {
    }

    public static Stream<LoginCase> missingCredentials() {
        return Stream.of(
                new LoginCase("Не заполнено имя пользователя", new Credentials("", STANDARD_USER.password()),
                        "Epic sadface: Username is required"),
                new LoginCase("Не заполнен пароль", new Credentials(STANDARD_USER.username(), ""),
                        "Epic sadface: Password is required"));
    }

    public record LoginCase(String name, Credentials credentials, String error) {
    }
}
