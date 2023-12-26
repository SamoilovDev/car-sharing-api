package org.all.files.database;

import org.all.files.dto.Car;
import org.all.files.dto.Company;
import org.all.files.dto.Customer;
import org.all.files.mechanisms.FieldCache;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManage {

    public static final Database DATABASE = new Database();

    public void addNewCompany(String companyName) {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("INSERT INTO carsharing.company (NAME) VALUES (?)");

            preparedStatement.setString(1, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Company> getCompanies() {
        try (ResultSet resultSet = DATABASE.getStatement().executeQuery("SELECT * FROM carsharing.company ORDER BY ID;")) {
            List<Company> companies = new ArrayList<>();

            while (resultSet.next()) {
                companies.add(
                        Company.builder()
                                .id(resultSet.getInt("ID"))
                                .name(resultSet.getString("NAME"))
                                .build()
                );
            }

            return companies;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void addNewCar(String companyName, int companyID) {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("INSERT INTO carsharing.car (NAME, COMPANY_ID) VALUES (?, ?)");

            preparedStatement.setString(1, companyName);
            preparedStatement.setInt(2, companyID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Car> getCars() {
        try (ResultSet resultSet = DATABASE.getStatement().executeQuery("SELECT * FROM carsharing.car ORDER BY ID;")) {
            List<Car> cars = new ArrayList<>();

            while (resultSet.next()) {
                cars.add(
                        Car.builder()
                                .id(resultSet.getInt("ID"))
                                .name(resultSet.getString("NAME"))
                                .companyID(resultSet.getInt("COMPANY_ID"))
                                .build()
                );
            }

            return cars;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addNewCustomer(String companyName) {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("INSERT INTO carsharing.customer (NAME) VALUES (?);");

            preparedStatement.setString(1, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void setRentedCarToCustomer(int carID) {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("UPDATE customer SET RENTED_CAR_ID = ? WHERE ID = ?;");
            preparedStatement.setInt(1, carID);
            preparedStatement.setInt(2, FieldCache.customer.id());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static Integer rentedCarID() {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("SELECT RENTED_CAR_ID FROM customer WHERE ID = ?;");
            preparedStatement.setInt(1, FieldCache.customer.id());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return (Integer) resultSet.getObject("RENTED_CAR_ID");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void returnCar() {
        try {
            PreparedStatement preparedStatement = DATABASE
                    .getConnection()
                    .prepareStatement("UPDATE customer SET RENTED_CAR_ID = NULL WHERE ID = ?;");

            preparedStatement.setInt(1, FieldCache.customer.id());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Customer> getCustomers() {
        try (ResultSet resultSet = DATABASE.getStatement().executeQuery("SELECT * FROM carsharing.customer ORDER BY ID;")) {
            List<Customer> customers = new ArrayList<>();

            while (resultSet.next()) {
                customers.add(
                        Customer.builder()
                                .id(resultSet.getInt("ID"))
                                .name(resultSet.getString("NAME"))
                                .rentedCarID(resultSet.getInt("RENTED_CAR_ID"))
                                .build()
                );
            }

            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}
