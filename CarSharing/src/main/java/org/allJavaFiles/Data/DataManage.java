package org.allJavaFiles.Data;

import org.allJavaFiles.Mechanisms.Logger;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManage extends Database {

    public void addNewCompany(String companyName) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO carsharing.company (NAME) VALUES (?)");

            preparedStatement.setString(1, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();

        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM carsharing.company ORDER BY ID;");
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("ID"),
                        resultSet.getString("NAME")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }



        return companies;
    }


    public void addNewCar(String companyName, int companyID) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO carsharing.car (NAME, COMPANY_ID) VALUES (?, ?)");

            preparedStatement.setString(1, companyName);
            preparedStatement.setInt(2, companyID);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM carsharing.car " +
                    "ORDER BY ID;");

            while (resultSet.next()) {

                cars.add(new Car(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return cars;
    }

    public void addNewCustomer(String companyName) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO carsharing.customer (NAME) VALUES (?)");

            preparedStatement.setString(1, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void setRentedCarToCustomer(int carID) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE customer SET RENTED_CAR_ID = ? WHERE ID = ?;");
            preparedStatement.setInt(1, carID);
            preparedStatement.setInt(2, Logger.getCustomer().getId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static Integer rentedCarID() {
        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT RENTED_CAR_ID FROM customer " +
                            "WHERE ID = " + Logger.getCustomer().getId());
            resultSet.next();

            return (Integer) resultSet.getObject("RENTED_CAR_ID");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void returnCar() {
        try {
            preparedStatement = connection.prepareStatement("UPDATE customer SET RENTED_CAR_ID = NULL WHERE ID = ?;")
            preparedStatement.setInt(Logger.getCustomer().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM carsharing.customer ORDER BY ID;");

            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("NAME"), resultSet.getInt("RENTED_CAR_ID")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return customers;
    }


}
