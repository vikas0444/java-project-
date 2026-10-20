package com.shopsphere.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String JDBC_URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/shopsphere?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            // Driver may also be loaded automatically by modern JDBC SPI
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbPassword == null || dbPassword.isBlank()) {
            throw new SQLException("Required environment variable is not set: DB_PASSWORD");
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, dbPassword);
    }
}
