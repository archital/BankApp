package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface BaseDAO {
    public Connection openConnection() throws SQLException;
    public void closeConnection();

}
