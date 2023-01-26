package daw.programacion;
import java.sql.*;

// Error No suitable driver found for jdbc:sqlite:identifier.sqlite

public class Conectar {

  private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

  public static String conectar(String SQL) {
    Connection conn = null;
    StringBuilder enunciado = new StringBuilder();

    try {
      // create a connection to the database
      conn = DriverManager.getConnection(DATABASE_URL);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);
      // from rs, the SQL print all the data
        while (rs.next()) {
          if (rs.getString("contestada").equals("0")) {
            // System.out.println(rs.getString("enunciado"));
            enunciado = new StringBuilder(rs.getString("enunciado"));
          }
        }
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
    return enunciado.toString();
  }
}
