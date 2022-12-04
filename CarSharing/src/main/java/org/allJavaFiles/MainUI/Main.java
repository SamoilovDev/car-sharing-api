package org.allJavaFiles.MainUI;

import org.allJavaFiles.Data.DataManage;

public class Main {

    public static final DataManage DATABASE = new DataManage();

    public static void main(String[] args) {
        DATABASE.getConnection();
        MainInterface.MAIN_INTERFACE.action();
//        DATABASE.dropTable("company");
//        DATABASE.dropTable("car");
//        DATABASE.dropTable("customer");
    }

}