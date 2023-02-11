package daw.programacion.ruleta.logic;

import daw.programacion.ruleta.sql.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {

    Panel panel = new Panel();
    String frase = Connector.conector(Connector.obtenerEnigmaNuevo());
    System.out.println(frase);
    Connector.conector(Connector.darBajaEigma(frase));
    // Panel.showConsonant();

  }
}
