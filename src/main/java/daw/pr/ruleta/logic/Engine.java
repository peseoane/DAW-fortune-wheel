package daw.pr.ruleta.logic;

import daw.pr.ruleta.Main;
import daw.pr.ruleta.SQL.SQLDriver;
import daw.pr.ruleta.struct.Enigma;
import daw.pr.ruleta.struct.Player;
import daw.pr.ruleta.struct.definitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Engine {

    static Logger logger = LogManager.getLogger(Main.class);

    private final SQLDriver SQLDriver;
    private final Player player;
    private final Enigma enigma;

    public Engine() {
        SQLDriver = new SQLDriver();
        player = registerPlayer();
        enigma = new Enigma(SQLDriver.getEnigma());
    }

    private Player registerPlayer() {
        String name = null;
        int money = 0;
        Boolean flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca su nombre");
                name = definitions.teclado.nextLine();
                flag = false;
                logger.info("El nombre introducido es válido");
            } catch (Exception e) {
                logger.error("El nombre introducido no es válido");
            }
        }
        flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca su dinero");
                money = definitions.teclado.nextInt();
                flag = false;
                logger.info("El dinero introducido es válido");
            } catch (Exception e) {
                logger.error("El dinero introducido no es válido");
            }
        }

        definitions.teclado.nextLine();
        logger.info("Nombre = " + name + " Dinero " + money);
        return new Player(name, money);
    }

    public int start() {
        logger.info("El juego ha comenzado");
        intentarResolverPanel();
        logger.info(player.getCasillaRuleta());
        return 1;
    }

    public boolean intentarResolverPanel() {
        System.out.println("Introduzca la respuesta");
        String respuesta = definitions.teclado.nextLine();
        if (respuesta.equals(enigma.getEnigma())) {
            System.out.println("Respuesta correcta");
            player.setMoney(player.getMoney() + 120);
            return true;
        }
        else {
            System.out.println("Respuesta incorrecta");
            return false;
        }
    }
}
