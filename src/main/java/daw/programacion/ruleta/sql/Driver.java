package daw.programacion.ruleta.sql;

import daw.programacion.ruleta.logic.Main;
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
                }
            }
            ResultSet rs2 = stmt.executeQuery(darBajaEigma(enigma.toString()));
            logger.info("Conectado a SQLite");
            logger.info(query);
            logger.info(rs2.getString("enigma"));
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
        return enigma.toString();
    }

    private String obtenerEnigmaNuevo() {
        return """
                SELECT enigma 
                FROM enigmas 
                WHERE contestada = 0 
                LIMIT 1;
                """;
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
