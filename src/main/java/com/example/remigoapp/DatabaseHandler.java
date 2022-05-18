package com.example.remigoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseHandler {
    Connection c = null;


    DatabaseHandler() throws SQLException, ClassNotFoundException {
        connect();
    }

    private void connect() throws SQLException, ClassNotFoundException {
            Class.forName(Constants.JCDB_CLASS);
            c = DriverManager.getConnection(Constants.JCDB_CONNECTION);
    }

    public void add(Memo memoInput){

    }

    public User getUserData(User userInput) throws SQLException {
        Statement statement = c.createStatement();
        User user = null;
        return user;
    }

}