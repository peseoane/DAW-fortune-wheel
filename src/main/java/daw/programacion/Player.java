package daw.programacion;

public class Player {
    private String name;
    private int money;
    private boolean turn;

    /**
     * En caso de que inicie el juego, el dinero empieza en 0
     * @param name nombre jugador
     */
    public Player(String name) {
        this.name = name;
        this.money = 0;
    }

    /**
     * Constructor cuando un jugador mantiene dinero de otra ronda.
     * @param name nombre de jugador
     * @param money sobrecarga, en caso de tener dinero especificarlo
     */
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
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

    public int buyVocal() {
        if (money >= definitions.CANTIDAD_VOCAL) {
            if ()
        }
    }
}
