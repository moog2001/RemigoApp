package com.example.remigoapp;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseHandler extends SQLi {


    DatabaseHandler(){
        connect();
    }

    private void connect() {
        Connection c = null;

        try {
            Class.forName(Constants.JCDB_CLASS);
            c = DriverManager.getConnection(Constants.JCDB_CONNECTION);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void add(Memo memoInput){

    }





}