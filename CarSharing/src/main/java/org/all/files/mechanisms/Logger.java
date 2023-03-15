package org.all.files.mechanisms;

import org.all.files.data.Car;
import org.all.files.data.Company;
import org.all.files.data.Customer;

public class Logger {

    public static boolean isLoggedCustomer;

    public static Company company;

    public static Customer customer;

    public static Car car;

    public static void rollBack() {
        isLoggedCustomer = false;
        company = null;
        customer = null;
        car = null;
    }

}
