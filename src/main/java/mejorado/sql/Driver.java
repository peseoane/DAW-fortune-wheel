package mejorado.sql;

import mejorado.logic.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Driver {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";
    static Logger logger = LogManager.getLogger(Main.class);
    String enigma;
    String pista;

    public Driver() {
        this.enigma = conector(obtenerEnigmaNuevo());
        // this.pista = conector(obtenerPista());
    }

    public String conector(String query) {
        Connection conn = null;
        StringBuilder enigma = new StringBuilder();
        StringBuilder enigmaSano = new StringBuilder();
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            logger.info("Conectado a SQLite");
            logger.info(query);
            logger.info(rs.getString("enigma"));
            while (rs.next()) {
                if (rs.getString("enigma") != null) {
                    enigma.append(rs.getString("enigma"));
                    // Eliminar de enigma todos los símbolos de puntuación, tiles y miscelanea
                    enigmaSano = sanitize(enigma);
                }
            }
            query = darBajaEigma(enigma.toString());
            int rowsUpdated = stmt.executeUpdate(query);
            logger.info(query);
            logger.info("\nFilas actualizadas: " + rowsUpdated);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        return enigmaSano.toString();
    }

    private String obtenerEnigmaNuevo() {
        return """
                SELECT enigma 
                FROM enigmas 
                WHERE contestada = 0 
                LIMIT 1;
                """;
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
        logger.info("Se ha sanado el input: " + enigma);
        return enigma;
    }

    private String darBajaEigma(String enigma) {
        return """
                UPDATE enigmas 
                SET contestada = 1 
                WHERE enigma = "%s";
                """.formatted(enigma);
    }

    public String getEnigma() {
        return enigma;
    }

    public String getPista() {
        return pista;
    }
}
