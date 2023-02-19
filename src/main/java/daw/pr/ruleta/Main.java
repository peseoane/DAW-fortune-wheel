package daw.pr.ruleta;

import daw.pr.ruleta.logic.Engine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        int turno = 0;

        Engine engine = new Engine();

        int numJugadores = engine.getNumeroJugadores();

        // generate random number between 0 and numJugadores
        int n = (int) (Math.random() * (numJugadores + 1));

    }
}
