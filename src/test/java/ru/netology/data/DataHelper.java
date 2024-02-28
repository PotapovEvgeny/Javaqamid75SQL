package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));
    public static String testName = "vasya";
    public static String testPassword = "qwerty123";

    private DataHelper() {}

    @Value
    public static class AuthData {
        String login;
        String password;
    }

    public static AuthData getAuthData() {
        return new AuthData(testName, testPassword);
    }

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    public static AuthData generateUser() {
        return new AuthData(generateLogin(), generatePassword());
    }

    public static String generateCode() {
        return faker.number().digits(6);
    }

}
