package daw.pr.ruleta.struct;

import daw.pr.ruleta.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    static Logger logger = LogManager.getLogger(Main.class);
    String casillaRuleta;
    private String name;
    private int money;
    private int moneyAcumulado;
    private boolean turn;
    private int comodin;

    /**
     * Constructor cuando un jugador mantiene dinero de otra ronda.
     *
     * @param name  nombre de jugador
     * @param money sobrecarga, en caso de tener dinero especificarlo
     */
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.comodin = 0;
        this.moneyAcumulado = 0;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        Player.logger = logger;
    }

    public int getMoneyAcumulado() {

        return moneyAcumulado;
    }

    public void setMoneyAcumulado(int moneyAcumulado) {
        this.moneyAcumulado = moneyAcumulado;
    }

    public int getComodin() {
        return comodin;
    }

    public void setComodin(int comodin) {
        this.comodin = comodin;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
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

}
