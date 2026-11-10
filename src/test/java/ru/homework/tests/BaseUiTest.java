package ru.homework.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.homework.pages.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;

abstract class BaseUiTest {
    private LoginPage loginPage;

    @BeforeAll
    static void configureBrowser() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = System.getProperty("baseUrl", "https://www.saucedemo.com/");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        Configuration.browserSize = "1280x720";
        Configuration.timeout = 10_000;
        Configuration.reportsFolder = "target/selenide-reports";
    }

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage().open();
    }

    protected final LoginPage loginPage() {
        return loginPage;
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
