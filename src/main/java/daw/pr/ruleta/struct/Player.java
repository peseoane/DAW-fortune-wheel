package daw.pr.ruleta.struct;

import daw.pr.ruleta.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    static Logger logger = LogManager.getLogger(Main.class);
    private final int force;
    String casillaRuleta;
    private String name;
    private int money;
    private boolean turn;
    /**
     * Constructor cuando un jugador mantiene dinero de otra ronda.
     *
     * @param name  nombre de jugador
     * @param money sobrecarga, en caso de tener dinero especificarlo
     */
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.force = (int) (Math.random() * (30 - 12 + 1) + 12);
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        Player.logger = logger;
    }

    public String getCasillaRuleta() {
        return casillaRuleta;
    }

    public void setCasillaRuleta(String casillaRuleta) {
        this.casillaRuleta = casillaRuleta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getForce() {
        return force;
    }

    public void buyVocal() {
        System.out.println("Que vocal quiere comprar?");
        String vocal = definitions.teclado.nextLine();
        if (money >= definitions.CANTIDAD_VOCAL) {
            switch (vocal.charAt(0)) {
                case 'a', 'e', 'i', 'o', 'u' -> money = money - definitions.CANTIDAD_VOCAL;
                default -> logger.error("El usuario no ha introducido vocal, turno perdido");
            }
        }
    }
}
