package org.all.files.mechanisms;

import lombok.experimental.UtilityClass;
import org.all.files.dto.Car;
import org.all.files.dto.Company;
import org.all.files.dto.Customer;

@UtilityClass
public class FieldCache {

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
