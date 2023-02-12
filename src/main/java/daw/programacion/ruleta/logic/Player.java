package daw.programacion.ruleta.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    static Logger logger = LogManager.getLogger(Main.class);

    private final int force;
    private final Panel panel;
    private String name;
    private int money;
    private boolean turn;

    /**
     * En caso de que inicie el juego, el dinero empieza en 0
     *
     * @param name nombre jugador
     */
    public Player(String name, Panel panel) {
        this.name = name;
        this.money = 0;
        this.force = (int) (Math.random() * (30 - 12 + 1) + 12);
        this.panel = panel;
    }

    /**
     * Constructor cuando un jugador mantiene dinero de otra ronda.
     *
     * @param name  nombre de jugador
     * @param money sobrecarga, en caso de tener dinero especificarlo
     */
    public Player(String name, int money, Panel panel) {
        this.name = name;
        this.money = money;
        this.force = (int) (Math.random() * (30 - 12 + 1) + 12);
        this.panel = panel;
    }

    /**
     * Método para resolver panel
     */
    public void resolvePanel() {
        mostrarPanel();
        System.out.println("Introduzca la frase que cree que es correcta");
        String frase = HEADERS.teclado.nextLine();
        if (frase.equals(panel.getEnigma())) {
            money += 100;
            logger.info("El usuario ha acertado el enigma, se le han añadido 100€");
        }
        else {
            money = 0;
            logger.info("El usuario ha fallado el enigma, se le han quitado todos los puntos");
        }
    }

    private void mostrarPanel() {
        System.out.println("El panel es el siguiente:");
        System.out.println(panel);
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
        String vocal = HEADERS.teclado.nextLine();
        if (money >= HEADERS.CANTIDAD_VOCAL) {
            switch (vocal.charAt(0)) {
                case 'a', 'e', 'i', 'o', 'u' -> money = money - HEADERS.CANTIDAD_VOCAL;
                default -> logger.error("El usuario no ha introducido vocal, turno perdido");
            }
        }
    }
}
