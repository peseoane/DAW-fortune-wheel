package daw.programacion;

import java.sql.*;

// Error No suitable driver found for jdbc:sqlite:identifier.sqlite

public class Sqlite {

  private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

  /*
  Conectarse a la base de datos y hacer una consulta que cumpla con las siguientes condiciones:
    - Que el enunciado no haya sido contestado
   */

  /*
    Conectarse a la base de datos y hacer una consulta que cumpla con las siguientes condiciones:
      - Que el enunciado haya sido contestado
      - Limitar la query a una sola tupla
      - Esa misa query, cambiar la condición para que no haya sido contestado a TRUE
      De la tabla "enigmas" seleccionar el enunciado que cumpla con las condiciones
     */

  public static String queryNoContestada() {
    return """
                SELECT enunciado 
                FROM enigmas 
                WHERE contestada = 0 
                LIMIT 1;
                """;
  }

  public static String anularQuery(String enigma) {
    return """
                UPDATE enigmas 
                SET contestada = 1 
                WHERE enunciado = "%s";
                """.formatted(
        enigma
      );
  }

  public static String conector(String query) {
    Connection conn = null;
    StringBuilder enunciado = new StringBuilder();

    try {
      // create a connection to the database
      conn = DriverManager.getConnection(DATABASE_URL);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      // from rs, the SQL print all the data

      System.out.println("Conectado a SQLite");
      while (rs.next()) {
        if (rs.getString("enunciado") != null) {
          enunciado.append(rs.getString("enunciado"));
        }
      }
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
