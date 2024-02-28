package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Text;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton= $("[data-test-id='action-login']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage validLogin (DataHelper.AuthData data){
        loginField.setValue(data.getLogin());
        passwordField.setValue(data.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(text(expectedText)).shouldBe(visible);
    }


}
