package org.allJavaFiles.MainUI;

import org.allJavaFiles.Mechanisms.Mechanisms;

import static org.allJavaFiles.MainUI.MainInterface.*;

public enum ManagerInterface implements UserInterface {
    MANAGER_INTERFACE {
        @Override
        public UserInterface action() {
            System.out.println("""
                        1. Company list
                        2. Create a company
                        0. Back""");

            return switch (SCANNER.next().trim()) {
                case "1" -> COMPANY_LIST.action();
                case "2" -> CREATE_COMPANY.action();
                case "0" -> BACK.action();
                default -> ERROR.action();
            };
        }
    },
    CREATE_COMPANY {
        @Override
        public UserInterface action() {
            System.out.println("Enter the company name:");

            SCANNER.nextLine();
            Main.DATABASE.addNewCompany(SCANNER.nextLine());

            System.out.println("The company was created!");

            return MANAGER_INTERFACE.action();
        }
    },
    COMPANY_LIST {
        @Override
        public UserInterface action() {
            return Mechanisms.getCompanyList();
        }
    },
    ERROR {
        @Override
        public UserInterface action() {
            System.out.println("Wrong num of action!");
            return MANAGER_INTERFACE.action();
        }
    }
}
