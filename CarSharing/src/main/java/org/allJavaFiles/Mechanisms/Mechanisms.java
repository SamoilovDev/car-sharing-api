package org.allJavaFiles.Mechanisms;

import org.allJavaFiles.Data.*;
import org.allJavaFiles.MainUI.Main;
import org.allJavaFiles.MainUI.UserInterface;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.allJavaFiles.MainUI.CustomerInterface.CUSTOMER_INTERFACE;
import static org.allJavaFiles.MainUI.MainInterface.*;
import static org.allJavaFiles.MainUI.CompanyInterface.COMPANY_INTERFACE;
import static org.allJavaFiles.MainUI.ManagerInterface.MANAGER_INTERFACE;

public class Mechanisms {
    public static UserInterface getCompanyList() {

        if (Main.DATABASE.getCompanies().isEmpty()) {
            System.out.println("The company list is empty!\n");
            return Logger.isLoggedCustomer() ? CUSTOMER_INTERFACE.action() : MANAGER_INTERFACE.action();
        } else {
            System.out.println("Choose the company:");
        }

        AtomicInteger number = new AtomicInteger(0);
        Main.DATABASE.getCompanies().forEach(s -> {
            number.getAndIncrement();
            System.out.printf("%s. %s%n", number, s.getName());
        });

        System.out.println("0. Back");

        return companyID(SCANNER.next().trim());
    }

    public static UserInterface getCarList() {

        if (Main.DATABASE.getCars().stream()
                .filter(c -> c.getCompanyID() == Logger.getCompany().getId())
                .filter(c -> !Logger.isLoggedCustomer() || isNotCarRented(c.getId()))
                .toList().isEmpty()) {

            System.out.println("The car list is empty!");

            return Logger.isLoggedCustomer() ? getCompanyList() : COMPANY_INTERFACE.action();
        } else {
            System.out.println(Logger.isLoggedCustomer() ? "Choose a car:" : "Car list:");

            AtomicInteger counter = new AtomicInteger(0);
            Main.DATABASE.getCars().stream()
                    .filter(c -> c.getCompanyID() == Logger.getCompany().getId())
                    .filter(c -> !Logger.isLoggedCustomer() || isNotCarRented(c.getId()))
                    .forEach(c -> {
                        counter.getAndIncrement();
                        System.out.printf("%s. %s%n", counter, c.getName());
                    });

        }
        System.out.println();

        return Logger.isLoggedCustomer() ?
                rentCar(SCANNER.next().trim(), Logger.getCompany().getId()) :  COMPANY_INTERFACE.action();
    }

    public static UserInterface getCustomerList() {

        if (Main.DATABASE.getCustomers().isEmpty()) {
            System.out.println("The customer list is empty!\n");
            return BACK.action();
        } else {
            System.out.println("Choose the customer:");
        }

        AtomicInteger number = new AtomicInteger(0);
        Main.DATABASE.getCustomers().forEach(s -> {
            number.getAndIncrement();
            System.out.printf("%s. %s%n", number, s.getName());
        });

        System.out.println("0. Back\n");

        return customerID(SCANNER.next().trim());
    }

    public static boolean isNotCarRented(int carID) {
        for (Customer customer : Main.DATABASE.getCustomers()) {
            if (customer.getRentedCarID() == carID) {
                return false;
            }
        }
        return true;
    }

    private static UserInterface rentCar(String request, int companyID) {
        try {
            int requestInt = Integer.parseInt(request);

            if (requestInt == 0) return Mechanisms.getCompanyList();

            Car car = Main.DATABASE.getCars().stream()
                    .filter(c -> c.getCompanyID() == companyID)
                    .toList()
                    .get(requestInt - 1);

            if (Objects.equals(car, null)) {
                System.out.println("Wrong number of action!\n");
                return Mechanisms.getCarList();
            } else {
                Main.DATABASE.setRentedCarToCustomer(car.getId());
                Logger.setCar(car);

                System.out.printf("You rented '%s'%n", car.getName());

                return CUSTOMER_INTERFACE.action();
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong action! Please, write a number.\n");
            return getCarList();
        }
    }

    private static UserInterface companyID(String request) {
        UserInterface menu = Logger.isLoggedCustomer() ?
                CUSTOMER_INTERFACE : MANAGER_INTERFACE;

        try {
            int requestInt = Integer.parseInt(request);

            if (requestInt == 0) return menu.action();

            try {
                Company company = Main.DATABASE.getCompanies().get(requestInt - 1);
                Logger.setCompany(company);
                return Logger.isLoggedCustomer() ? getCarList() : COMPANY_INTERFACE.action();
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Wrong number of action!\n");
                return menu.action();
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong action! Please, write a number.\n");
            return menu.action();
        }

    }

    private static UserInterface customerID(String request) {
        try {
            int requestInt = Integer.parseInt(request);

            if (requestInt == 0) return BACK.action();

            try {
                Customer customer = Main.DATABASE.getCustomers().get(requestInt - 1);

                Logger.setCustomer(customer);
                checkCustomerByRent();

                return CUSTOMER_INTERFACE.action();
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Wrong number of action!\n");
                return BACK.action();
            }


        } catch (NumberFormatException e) {
            System.out.println("Wrong action! Please, write a number.\n");
            return BACK.action();
        }
    }

    private static void checkCustomerByRent() {
        if (! Objects.equals(DataManage.rentedCarID(), null)) {
            Car car = Main.DATABASE.getCars().get(DataManage.rentedCarID() - 1);
            Logger.setCar(car);
            Logger.setCompany(Main.DATABASE.getCompanies().get(Logger.getCar().getCompanyID() - 1));
        }
    }

}
