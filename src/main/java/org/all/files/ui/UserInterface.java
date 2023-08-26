package org.all.files.ui;

public sealed interface UserInterface permits MainInterface, ManagerInterface, CompanyInterface, CustomerInterface {
     UserInterface action();
}
