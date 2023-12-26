package org.all.files.ui;

import org.all.files.Main;
import org.all.files.mechanisms.Mechanisms;
import org.all.files.mechanisms.FieldCache;

import static org.all.files.ui.MainInterface.SCANNER;

public enum CompanyInterface implements UserInterface {
    COMPANY_INTERFACE {
        @Override
        public UserInterface action() {
            System.out.printf("""
                    '%s' company
                    1. Car list
                    2. Create a car
                    0. Back
                    """, FieldCache.customer.name());

            return switch (SCANNER.next().trim()) {
                case "1" -> CAR_LIST.action();
                case "2" -> CREATE_CAR.action();
                case "0" -> ManagerInterface.MANAGER_INTERFACE.action();
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
            Main.DATABASE.addNewCar(SCANNER.nextLine().trim(), FieldCache.customer.id());

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
