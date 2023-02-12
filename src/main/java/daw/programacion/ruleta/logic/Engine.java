package daw.programacion.ruleta.logic;

import daw.programacion.ruleta.sql.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Engine {

    static Logger logger = LogManager.getLogger(Main.class);

    private final Driver driver;
    private final Player player;
    private final Wheel wheel;
    private final Panel panel;

    public Engine() {
        driver = new Driver();
        player = registerPlayer();
        panel = new Panel(driver.getEnigma());
        wheel = new Wheel(player);
    }

    private Player registerPlayer() {
        String name = null;
        int money = 0;
        Boolean flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca su nombre");
                name = HEADERS.teclado.nextLine();
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
                money = HEADERS.teclado.nextInt();
                flag = false;
                logger.info("El dinero introducido es válido");
            } catch (Exception e) {
                logger.error("El dinero introducido no es válido");
            }
        }

        HEADERS.teclado.nextLine();
        logger.info("Nombre = " + name + " Dinero " + money);
        return new Player(name, money);
    }

    public int start() {
        logger.info("El juego ha comenzado");
        intentarResolverPanel();
        wheel.girarRuleta(player);
        logger.info(player.getCasillaRuleta());

        return 1;
    }

    public boolean intentarResolverPanel() {
        System.out.println("Introduzca la respuesta");
        String respuesta = HEADERS.teclado.nextLine();
        if (respuesta.equals(panel.getEnigma())) {
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
