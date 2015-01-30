package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public class BaseDAOImpl implements BaseDAO {
    private Connection conn;



    @Override
    public Connection openConnection() throws SQLException {

        String db_name = "BankApplication";
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/" + db_name,
                "sa", // login
                "" // password
        );
        return conn;
    }

    @Override
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}