package daw.programacion.ruleta.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Engine {

    static Logger logger = LogManager.getLogger(Main.class);

/**
 private final Driver driver;
 private final Player player;
 private final Wheel wheel;
 private Panel panel;

 public Engine() {

 driver = new Driver();
 player = new Player("Jugador", 0, panel);
 panel = new Panel();
 wheel = new Wheel();

 }

 private Player registerPlayer() {
 Boolean flag = true;
 while (flag) {
 try {
 System.out.println("Introduzca su nombre");
 String name = HEADERS.teclado.nextLine();
 flag = false;
 } catch (RuntimeException e) {
 logger.error("El nombre introducido no es válido");
 }
 }
 int money = HEADERS.teclado.nextInt();
 HEADERS.teclado.nextLine();
 return new Player(name, money, panel);
 }
 **/
}
