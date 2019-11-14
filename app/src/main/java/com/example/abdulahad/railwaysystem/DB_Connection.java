package com.example.abdulahad.railwaysystem;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Abdul Ahad on 4/5/2018.
 */

public class DB_Connection {

    private static final String TAG = "Connection Error TAG";

    public Connection getConnection() {
        Connection connection=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12232736","sql12232736","Tz3BDJJGIe");
        }
        catch (Exception e) {
            System.out.println(e);
            Log.e(TAG,"Connection Error");
        }
        return connection;
    }

}



