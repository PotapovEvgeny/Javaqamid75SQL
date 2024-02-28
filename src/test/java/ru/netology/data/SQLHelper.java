package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var verCode = "SELECT code FROM auth_codes";
        return runner.query(getConn(), verCode, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        runner.execute(getConn(),"DELETE FROM auth_codes");
        runner.execute(getConn(), "DELETE FROM cards");
        runner.execute(getConn(),"DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuth_code() {
        runner.execute(getConn(), "DELETE FROM auth_codes");
    }

    @SneakyThrows
    public static String getStatus() {
        var status = "SELECT status FROM users WHERE login = 'vasya'";
        return runner.query(getConn(), status, new ScalarHandler<String>());
    }
}
