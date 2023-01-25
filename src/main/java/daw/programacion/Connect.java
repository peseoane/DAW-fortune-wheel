package daw.programacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Error No suitable driver found for jdbc:sqlite:identifier.sqlite

public class Connect {

    private final static String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    public static void main(String[] args) {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(DATABASE_URL);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
