package daw.programacion.ruleta.logic;

import daw.programacion.ruleta.logic.enigma.Panel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {


        Player tester = new Player("Pepe", 100, new Panel("Hola que tal estas"));
        
        tester.resolvePanel();

    }
}
