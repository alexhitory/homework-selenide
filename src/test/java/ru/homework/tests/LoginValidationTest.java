package ru.homework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;

import java.time.Duration;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Tag("auth")
@Tag("negative")
@DisplayName("Валидация обязательных полей входа")
class LoginValidationTest extends BaseUiTest {
    @TestFactory
    @DisplayName("Ошибка при отсутствии обязательного поля")
    Stream<DynamicTest> missingCredentials() {
        return Stream.of(
                new LoginCase("Не заполнено имя пользователя", "", "secret_sauce",
                        "Epic sadface: Username is required"),
                new LoginCase("Не заполнен пароль", "standard_user", "",
                        "Epic sadface: Password is required")
        ).map(testCase -> dynamicTest(testCase.name(), () ->
                assertTimeout(Duration.ofSeconds(45), () -> {
                    try {
                        loginPage().open()
                                .attemptLogin(testCase.username(), testCase.password())
                                .shouldShowError(testCase.error());
                    } finally {
                        closeWebDriver();
                    }
                })));
    }
    private record LoginCase(String name, String username, String password, String error) {
    }
}
