package daw.programacion.ruleta.logic;

import daw.programacion.ruleta.logic.enigma.Panel;
import daw.programacion.ruleta.sql.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            String query = Connector.obtenerEnigmaNuevo();
            logger.info("SQL echo -> \n" + query);
            String frase = Connector.conector(query);
            logger.info("SQL echo -> \n" + frase);
            String deleteQuery = Connector.darBajaEigma(frase);
            logger.info("Se ha dado de baja el enigma: " + deleteQuery);
            Connector.conector(deleteQuery);
            // print the length of the string frase
            logger.info("La longitud de la frase es: " + frase.length());
            Panel panel = new Panel("Despiden al profe con una cadena humana de aplausos");
            System.out.println(panel);
            logger.info("Panel inicializado correctamente");
        } catch (IndexOutOfBoundsException e) {
            logger.error("Comprueba el SQL, hay paneles que no caben en la pantalla!");
        }


    }
}
