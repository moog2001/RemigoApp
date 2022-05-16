package com.example.remigoapp;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseHandler {


    DatabaseHandler(){
        //connect();
    }

    private void connect() {
        Connection c = null;

        try {
            Class.forName(Constants.JCDBClass);
            c = DriverManager.getConnection(Constants.JCDBConnection);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }




}