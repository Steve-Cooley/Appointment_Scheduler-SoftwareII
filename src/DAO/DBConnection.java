package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Mostly inspired by Sherilyn Decusatis'  DAODemo
//I'm not really sure why the URL has to be divided into different strings, but both instructors I've paid attention
// to on this topic did so.

public class DBConnection {
    private static final String DBName = "U067tn";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com" + DBName;
    private static final String username = "U067tn";
    private static final String password = "53688695909";
    private static final String driver = "com.mysql.jdbc.Driver";   //If there is a problem, I suspect this is where it
                                                                    //will be.  I'm using intellij, not netbeans, and
                                                                    //the way I imported the driver was not the same
                                                                    //as the CI's, and I don't fully understand it.
    static Connection conn;

    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection Successful");
    }
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed");
    }
}

