package org.all.files.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
public class Database {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/carsharing";

    private static final String USER = "root";

    private static final String PASS = "qq11qq22qq33";

    public static Connection connection;

    public static PreparedStatement preparedStatement;

    public static Statement statement;


    public void getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(true);
            createTables();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to get connection with db, check the url: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createTables() throws SQLException {
        statement = connection.createStatement();

        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS COMPANY (
                    ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR(255) UNIQUE NOT NULL
                );""");
        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS CAR (
                    ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR(255) UNIQUE NOT NULL,
                    COMPANY_ID INT UNSIGNED,
                    FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
                );""");
        statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS CUSTOMER (
                    ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                    NAME VARCHAR(255) UNIQUE NOT NULL,
                    RENTED_CAR_ID INT UNSIGNED DEFAULT NULL,
                    FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
                );""");
    }

    public void closeDB() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS ".concat(tableName.trim().toUpperCase()));
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

}

//DBMS: MySQL (ver. 8.0.31)
//        Case sensitivity: plain=lower, delimited=lower
//        Driver: MySQL Connector/J (ver. mysql-connector-java-8.0.25 (Revision: 08be9e9b4cba6aa115f9b27b215887af40b159e0), JDBC4.2)
//
//        Ping: 38 ms
//        SSL: yes