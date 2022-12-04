package org.allJavaFiles.Mechanisms;

import org.allJavaFiles.Data.Car;
import org.allJavaFiles.Data.Company;
import org.allJavaFiles.Data.Customer;

public class Logger {

    private static boolean isLoggedCustomer = false;

    private static Company company;

    private static Customer customer;

    private static Car car;

    public static boolean isLoggedCustomer() {
        return isLoggedCustomer;
    }

    public static void setIsLoggedCustomer(boolean isLoggedCustomer) {
        Logger.isLoggedCustomer = isLoggedCustomer;
    }

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company company) {
        Logger.company = company;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        Logger.customer = customer;
    }

    public static Car getCar() {
        return car;
    }

    public static void setCar(Car car) {
        Logger.car = car;
    }
}
