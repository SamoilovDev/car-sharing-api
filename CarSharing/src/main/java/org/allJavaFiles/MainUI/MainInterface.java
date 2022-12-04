package org.allJavaFiles.MainUI;

import org.allJavaFiles.Mechanisms.Logger;
import org.allJavaFiles.Mechanisms.Mechanisms;

import java.util.Scanner;

public enum MainInterface implements UserInterface {
    MAIN_INTERFACE {
        @Override
        public UserInterface action() {

            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit""");

            return switch (SCANNER.next().trim()) {
                case "1" -> LOG_IN_MANAGER.action();
                case "2" -> LOG_IN_CUSTOMER.action();
                case "3" -> CREATE_CUSTOMER.action();
                case "0" -> EXIT.action();
                default -> ERROR.action();
            };

        }

    },
    LOG_IN_MANAGER {
        @Override
        public UserInterface action() {
            return ManagerInterface.MANAGER_INTERFACE.action();
        }
    },
    LOG_IN_CUSTOMER {
        @Override
        public UserInterface action() {
            Logger.setIsLoggedCustomer(true);
            return Mechanisms.getCustomerList();
        }
    },
    CREATE_CUSTOMER {
        @Override
        public UserInterface action() {
            System.out.println("Enter the customer name:");

            SCANNER.nextLine();
            Main.DATABASE.addNewCustomer(SCANNER.nextLine().trim());
            System.out.println("The customer was added!");

            return MAIN_INTERFACE.action();
        }
    },
    BACK {
        @Override
        public UserInterface action() {
            Logger.setCar(null);
            Logger.setCustomer(null);
            Logger.setCompany(null);
            Logger.setIsLoggedCustomer(false);
            return MAIN_INTERFACE.action();
        }
    },
    EXIT {
        @Override
        public UserInterface action() {
            Main.DATABASE.closeDB();
            return EXIT;
        }
    },
    ERROR {
        @Override
        public UserInterface action() {
            System.out.println("Wrong num of action!");
            return MAIN_INTERFACE.action();
        }
    };

    public static final Scanner SCANNER = new Scanner(System.in);

}
