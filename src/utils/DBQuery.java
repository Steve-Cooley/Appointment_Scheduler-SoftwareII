package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement;

    // create statement object
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    //Return statement Object
    public static Statement getStatement() {
        return statement;
    }

}
