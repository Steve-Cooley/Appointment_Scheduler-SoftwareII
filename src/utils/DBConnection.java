package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Mostly inspired by Sherilyn Decusatis'  DAODemo
//I'm not really sure why the URL has to be divided into different strings, but both instructors I've paid attention
// to on this topic did so (in different ways)

public class DBConnection {
    // parts  (Why are we supposed to separate these components?)
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String addr = "//wgudb.ucertify.com/U067tn";

    // assembled parts
    private static final String jdbcURL= protocol + vendorName + addr;

    // Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U067tn";
    private static final String password = "53688695909";

    //Requirement F "mechanisms" 1/2 (try/catch)
    public static Connection startConnection()  {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e) {
            System.out.println("classnotfound " + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("sqlexception " +e.getMessage());
        }
        return conn;
    }
    //Requirement F "mechanisms" 2/2 (throws)
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed");
    }
}

