package ru.netology.test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.page.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class BankLoginTest {
    LoginPage loginPage;


    @AfterEach
    void cleanCode() {
        cleanAuth_code();
    }

    @AfterAll
    static void cleanAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login with registered user and right code")
    void shouldPositiveLogin() {
        var verificationPage = loginPage.validLogin(getAuthData());
        verificationPage.verificationPageIsVisible();
        verificationPage.validVerify(getVerificationCode());
    }

    @Test
    @DisplayName("Should get error when invalid login")
    void shouldNotLoginWithInvalidLogin() {
        loginPage.validLogin(new AuthData(testName, generatePassword()));
        loginPage.findErrorMessage("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error when invalid password")
    void shouldNotLoginWithInvalidPassword() {
        loginPage.validLogin(new AuthData(generateLogin(), testPassword));
        loginPage.findErrorMessage("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error when invalid login and password")
    void shouldNotLoginWithInvalidLoginAndPassword() {
        loginPage.validLogin(generateUser());
        loginPage.findErrorMessage("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error when invalid verificationCode")
    void shouldNotLoginWithInvalidCode() {
        var verificationPage = loginPage.validLogin(getAuthData());
        verificationPage.verify(generateCode());
        verificationPage.findErrorMessage("Неверно указан код!");
    }

    @Test
    @DisplayName("Should blocked user when invalid password more 3 times enter")
    void shouldBlockedWhenInvalidPasswordEnterMoreTimes() {
        for (int i = 0; i < 3; i++) {
            refresh();
            loginPage.validLogin(new AuthData(testName, generatePassword()));
            loginPage.findErrorMessage("Неверно указан логин или пароль");
        }
        String expected = "blocked";
        String actual = getStatus();
        Assertions.assertEquals(expected, actual);
    }


}
