package com.example.mateusz.kosmicznaprzygoda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;






    public void Connect () {
        this.connection = makeConnection();
    }

    public static java.sql.Connection getConnection() {
        return(connection);
    }

    public java.sql.Connection makeConnection() {

        String url = "jdbc:postgresql://195.150.230.210:5434/2018_wilk_mateusz";

        try {
            Class.forName("org.postgresql.Driver");
            java.sql.Connection con = DriverManager.getConnection(url, "2018_wilk_mateusz", "29558");


            return(con);
        }
        catch(ClassNotFoundException cnfe)
        {
            System.err.println("Blad ladowania sterownika: " + cnfe);
            return(null);
        }
        catch(SQLException sqle)
        {

            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);

            return(null);
        }
    }


}
