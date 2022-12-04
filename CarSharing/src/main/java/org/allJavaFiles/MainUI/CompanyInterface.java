package org.allJavaFiles.MainUI;

import org.allJavaFiles.Mechanisms.Logger;
import org.allJavaFiles.Mechanisms.Mechanisms;

import static org.allJavaFiles.MainUI.MainInterface.SCANNER;
import static org.allJavaFiles.MainUI.ManagerInterface.MANAGER_INTERFACE;

public enum CompanyInterface implements UserInterface {
    COMPANY_INTERFACE {
        @Override
        public UserInterface action() {
            System.out.printf("""
                    '%s' company
                    1. Car list
                    2. Create a car
                    0. Back
                    """, Logger.getCompany().getName());

            return switch (SCANNER.next().trim()) {
                case "1" -> CAR_LIST.action();
                case "2" -> CREATE_CAR.action();
                case "0" -> MANAGER_INTERFACE.action();
                default -> ERROR.action();
            };
        }
    },
    CAR_LIST {
        @Override
        public UserInterface action() {
            return Mechanisms.getCarList();
        }
    },
    CREATE_CAR {
        @Override
        public UserInterface action() {
            System.out.println("Enter the car name:");

            SCANNER.nextLine();
            Main.DATABASE.addNewCar(SCANNER.nextLine().trim(),
                    Logger.getCompany().getId());

            System.out.println("The car was added!");

            return COMPANY_INTERFACE.action();
        }
    },
    ERROR {
        @Override
        public UserInterface action() {
            System.out.println("Wrong num of action!");
            return COMPANY_INTERFACE.action();
        }
    }

}
