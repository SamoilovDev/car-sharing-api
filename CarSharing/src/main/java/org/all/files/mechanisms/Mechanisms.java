package org.all.files.mechanisms;

import org.all.files.data.Car;
import org.all.files.data.Customer;
import org.all.files.dto.DataManage;
import org.all.files.Main;
import org.all.files.ui.UserInterface;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.all.files.ui.CustomerInterface.CUSTOMER_INTERFACE;
import static org.all.files.ui.MainInterface.*;
import static org.all.files.ui.CompanyInterface.COMPANY_INTERFACE;
import static org.all.files.ui.ManagerInterface.MANAGER_INTERFACE;

public class Mechanisms {
    public static UserInterface getCompanyList() {
        if (Main.DATABASE.getCompanies().isEmpty()) {
            System.out.println("The company list is empty!\n");

            return Logger.isLoggedCustomer
                    ? CUSTOMER_INTERFACE.action()
                    : MANAGER_INTERFACE.action();
        } else {
            System.out.println("Choose the company:");
            AtomicInteger number = new AtomicInteger(0);
            Main.DATABASE.getCompanies().forEach(s -> {
                number.getAndIncrement();
                System.out.printf("%s. %s%n", number, s.name());
            });
            System.out.println("0. Back");

            return companyID(SCANNER.next().trim());
        }
    }

    public static UserInterface getCarList() {

        if (Main.DATABASE.getCars().stream()
                .filter(c -> c.companyID() == Logger.company.id())
                .filter(c -> !Logger.isLoggedCustomer || isNotCarRented(c.id()))
                .toList().isEmpty()) {

            System.out.println("The car list is empty!");

            return Logger.isLoggedCustomer ? getCompanyList() : COMPANY_INTERFACE.action();
        } else {
            System.out.println(Logger.isLoggedCustomer ? "Choose a car:" : "Car list:");

            AtomicInteger counter = new AtomicInteger(0);
            Main.DATABASE.getCars().stream()
                    .filter(c -> c.companyID() == Logger.company.id())
                    .filter(c -> !Logger.isLoggedCustomer || isNotCarRented(c.id()))
                    .forEach(c -> {
                        counter.getAndIncrement();
                        System.out.printf("%s. %s%n", counter, c.name());
                    });

            System.out.println();

            return Logger.isLoggedCustomer
                    ? rentCar(SCANNER.next().trim(), Logger.company.id())
                    :  COMPANY_INTERFACE.action();
        }
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
            System.out.printf("%s. %s%n", number, s.name());
        });

        System.out.println("0. Back\n");

        return customerID(SCANNER.next().trim());
    }

    public static boolean isNotCarRented(int carID) {
        for (Customer customer : Main.DATABASE.getCustomers()) {
            if (customer.rentedCarID() == carID) {
                return false;
            }
        }
        return true;
    }

    private static UserInterface rentCar(String request, int companyID) {
        try {
            int requestInt = Integer.parseInt(request);

            if (requestInt == 0) return Mechanisms.getCompanyList();

            Optional<Car> optionalCar = Main.DATABASE.getCars().stream()
                    .filter(c -> c.companyID() == companyID)
                    .findFirst();

            if (optionalCar.isEmpty()) {
                System.out.println("Wrong number of action!\n");
                return Mechanisms.getCarList();
            } else {
                Car car = optionalCar.get();
                Main.DATABASE.setRentedCarToCustomer(car.id());
                Logger.car = car;

                System.out.printf("You rented '%s'%n", car.name());

                return CUSTOMER_INTERFACE.action();
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong action! Please, write a number.\n");
            return getCarList();
        }
    }

    private static UserInterface companyID(String request) {
        UserInterface menu = Logger.isLoggedCustomer ?
                CUSTOMER_INTERFACE : MANAGER_INTERFACE;

        try {
            int requestInt = Integer.parseInt(request);

            if (requestInt == 0) return menu.action();

            try {
                Logger.company = Main.DATABASE.getCompanies().get(requestInt - 1);
                return Logger.isLoggedCustomer
                        ? getCarList()
                        : COMPANY_INTERFACE.action();
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
                Logger.customer = Main.DATABASE.getCustomers().get(requestInt - 1);

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
            Logger.car = Main.DATABASE.getCars().get(DataManage.rentedCarID() - 1);
            Logger.company = Main.DATABASE.getCompanies().get(Logger.car.companyID() - 1);
        }
    }

}
