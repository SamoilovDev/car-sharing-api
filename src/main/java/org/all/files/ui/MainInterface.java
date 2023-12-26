package org.all.files.ui;

import org.all.files.Main;
import org.all.files.database.DataManage;
import org.all.files.mechanisms.FieldCache;
import org.all.files.mechanisms.Mechanisms;

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
                case "1" -> ManagerInterface.MANAGER_INTERFACE.action();
                case "2" -> {
                    FieldCache.isLoggedCustomer = true;
                    yield Mechanisms.getCustomerList();
                }
                case "3" -> CREATE_CUSTOMER.action();
                case "0" -> EXIT.action();
                default -> {
                    System.out.println("Wrong num of action!");
                    yield MAIN_INTERFACE.action();
                }
            };

        }

    },
    CREATE_CUSTOMER {
        @Override
        public UserInterface action() {
            System.out.println("Enter the customer name:");

            MainInterface.SCANNER.nextLine();
            Main.DATABASE.addNewCustomer(SCANNER.nextLine().trim());
            System.out.println("The customer was added!");

            return MAIN_INTERFACE.action();
        }
    },
    BACK {
        @Override
        public UserInterface action() {
            FieldCache.rollBack();
            return MAIN_INTERFACE.action();
        }
    },
    EXIT {
        @Override
        public UserInterface action() {
            DataManage.DATABASE.closeDB();
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
