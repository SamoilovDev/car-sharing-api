package org.all.files.database;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Objects;

@Data
@Slf4j
public class Database {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carsharing";
    private static final String USER = "root";
    private static final String PASS = "qq11qq22qq33";
    private static final String CREATE_COMPANY_TABLE = """
            CREATE TABLE IF NOT EXISTS COMPANY (
                ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL
            );""";
    private static final String CREATE_CAR_TABLE = """
            CREATE TABLE IF NOT EXISTS CAR (
                ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL,
                COMPANY_ID INT UNSIGNED,
                FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
                ON DELETE SET NULL
                ON UPDATE CASCADE
            );""";
    private static final String CREATE_CUSTOMER_TABLE = """ 
            CREATE TABLE IF NOT EXISTS CUSTOMER (
                ID INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL,
                RENTED_CAR_ID INT UNSIGNED DEFAULT NULL,
                FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)
                ON DELETE SET NULL
                ON UPDATE CASCADE
        );""";

    private final Connection connection;

    private final Statement statement;

    public Database() {
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            this.statement = connection.createStatement();
            connection.setAutoCommit(true);
            this.createTables();
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Failed to get connection with db, check the url: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createTables() throws SQLException {
        statement.executeUpdate(CREATE_COMPANY_TABLE);
        statement.executeUpdate(CREATE_CAR_TABLE);
        statement.executeUpdate(CREATE_CUSTOMER_TABLE);
    }

    public void closeDB() {
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (SQLException se) {
            log.error("Failed to close connection with db: " + se.getMessage());
        }
    }

    public void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS ".concat(tableName.trim().toUpperCase()));
        } catch (SQLException e) {
            log.error("Failed to drop table: " + e.getMessage());
        }
    }

}
