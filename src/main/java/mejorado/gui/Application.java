package mejorado.gui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mejorado.logic.HEADERS;
import mejorado.logic.Panel;
import mejorado.logic.Player;
import mejorado.logic.Wheel;
import mejorado.sql.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application extends javafx.application.Application {
    static Logger logger = LogManager.getLogger(Application.class);
    private final Driver driver;
    private final Player player;
    private final Wheel wheel;
    private final Panel panel;

    public Application(Driver driver, Player player, Wheel wheel, Panel panel) {
        this.driver = driver;
        this.player = registerPlayer();
        this.wheel = new Wheel(player);
        this.panel = new Panel(driver.getEnigma());
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ruleta");
        logger.info("El juego ha comenzado");


        intentarResolverPanel();
        wheel.girarRuleta(player);
        logger.info(player.getCasillaRuleta());

        StackPane root = new StackPane();
        root.getChildren().add(new javafx.scene.control.Label("Aquí deberías agregar el panel"));
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        Tablero tablero = new Tablero();
        tablero.dibujarPanel(panel);
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
