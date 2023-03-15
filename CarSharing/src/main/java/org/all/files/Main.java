package org.all.files;

import org.all.files.dto.DataManage;
import org.all.files.ui.MainInterface;

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