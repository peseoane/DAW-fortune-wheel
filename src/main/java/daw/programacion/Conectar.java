package daw.programacion;
import java.sql.*;

// Error No suitable driver found for jdbc:sqlite:identifier.sqlite

public class Conectar {

  private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

  public static String conectar(String SQL) {
    Connection conn = null;
    try {
      // create a connection to the database
      conn = DriverManager.getConnection(DATABASE_URL);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);
        System.out.println(rs.getString("enigma"));
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
    return null;
  }
}
