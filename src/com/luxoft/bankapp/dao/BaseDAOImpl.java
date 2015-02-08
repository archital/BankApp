package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public class BaseDAOImpl implements BaseDAO {


    private static BaseDAOImpl instance;

    private BaseDAOImpl() {
    }

    public static  BaseDAOImpl getInstance() {
        if (instance == null) {
            instance = new  BaseDAOImpl();
        }
        return instance;
    }


    private Connection conn;



    @Override
    public synchronized Connection openConnection()  {

        String db_name = "BankApplication";
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/" + db_name,
                     "sa", // login
                     "" // password
             );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public synchronized void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
