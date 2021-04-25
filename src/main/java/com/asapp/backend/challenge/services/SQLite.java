package com.asapp.backend.challenge.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.asapp.backend.challenge.utils.Constants;

public class SQLite {
    SQLite(){
        Connection newConn = connect();
        createTables(newConn);
        try{
            newConn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTables(Connection conn){
        String persons_sql = "CREATE TABLE IF NOT EXISTS persons (\n"
                        +    "id integer NOT NULL,\n"
                        +    "username text PRIMARY KEY,\n"
                        +    "password text NOT NULL,\n"
                        +    "salt text NOT NULL,\n"
                        +    "token text,\n"
                        +    "date_token_created text);";

        try (Statement newTable_stmt = conn.createStatement()) {
            newTable_stmt.execute(persons_sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //TODO create messages table

    }

    public Connection connect(){
        String url = "jdbc:sqlite:" + Constants.databaseName;
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
            }
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
}
