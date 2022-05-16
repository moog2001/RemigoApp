package com.example.remigoapp;

import java.util.Timer;
import java.util.TimerTask;

public class Manager {
    User currentUser = new User();
    Timer timer  = new Timer ();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

        }
    };

    public Manager(User currentUser) {
        this.currentUser = currentUser;
    }



    public Manager(){};
}
