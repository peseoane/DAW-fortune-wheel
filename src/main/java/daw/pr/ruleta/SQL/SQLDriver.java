package daw.pr.ruleta.SQL;

import daw.pr.ruleta.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SQLDriver {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";
    static Logger logger = LogManager.getLogger(Main.class);
    String enigma;
    String pista;

    public SQLDriver() {
        enigma = getEnigma(); // Patch! También lo anula por acomplamiento...
        pista = getPista();
    }

    public String getEnigma() {
        String enigma = ejecutarQuery(obtenerEnigmaNuevo());
        ejecutarActualizacion(darDeBaja(enigma));
        return enigma;
    }

    public String getPista() {
        return ejecutarQuery(obtenerPista(this.enigma));
    }

    private String ejecutarQuery(String query) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.getString(1);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private String obtenerEnigmaNuevo() {
        return """
                SELECT enigma 
                FROM enigmas 
                WHERE contestada = 0 
                LIMIT 1;
                """;
    }

    private String ejecutarActualizacion(String query) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    // get the pista from a enigma using SQL

    private String darDeBaja(String enigma) {
        return """
                UPDATE enigmas 
                SET contestada = 1 
                WHERE enigma = "%s";
                """.formatted(enigma);
    }

    private String obtenerPista(String enigma) {
        return """
                SELECT pista 
                FROM enigmas 
                WHERE enigma = "%s";
                """.formatted(enigma);
    }

    private StringBuilder sanitize(StringBuilder enigma) {
        logger.info("Se va a sanear el input: " + enigma);
        enigma = new StringBuilder(enigma.toString().toUpperCase());
        enigma = new StringBuilder(enigma.toString().replaceAll("[ÁÀÂÄ]", "A"));
        enigma = new StringBuilder(enigma.toString().replaceAll("[ÉÈÊË]", "E"));
        enigma = new StringBuilder(enigma.toString().replaceAll("[ÍÌÎÏ]", "I"));
        enigma = new StringBuilder(enigma.toString().replaceAll("[ÓÒÔÖ]", "O"));
        enigma = new StringBuilder(enigma.toString().replaceAll("[ÚÙÛÜ]", "U"));
        enigma = new StringBuilder(enigma.toString().replaceAll("[.,;:]", ""));
        enigma = new StringBuilder(enigma.toString().replaceAll("[¿?¡!]", ""));
        enigma = new StringBuilder(enigma.toString().replaceAll("[\\s]", ""));
        logger.info("Se ha sanado el input: " + enigma);
        return enigma;
    }
}