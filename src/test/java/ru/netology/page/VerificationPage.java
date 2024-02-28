package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Code;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");


    public void verificationPageIsVisible() {
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify (String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }

    public void verify (String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(text(expectedText)).shouldBe(visible);
    }

}
