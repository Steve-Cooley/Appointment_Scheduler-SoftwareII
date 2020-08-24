package utils;

import Model.Address;

import java.sql.*;
import java.time.LocalDateTime;

public class DBQuery {

    private static PreparedStatement statement;

    // create statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS); //
        //added ', Statement.RETURN_GENERATED_KEYS' in an attempt to fix external key problems.  Might not work.
    }

    //Return statement Object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
    }
