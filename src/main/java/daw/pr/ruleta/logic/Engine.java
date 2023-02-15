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
    private final SQLDriver sql = new SQLDriver();
    private final Enigma enigma = new Enigma(sql);
    private final String frase = enigma.getEnigma();
    private final String pista;
    private final char[][] enigmaPanel;
    private Player player = new Player("test", 0);
    private final Ruleta ruleta = new Ruleta(player);

    public Engine() {
        this.player = registerPlayer();
        this.pista = sql.getPista();
        this.enigmaPanel = enigma.getPanel();
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

    public char[][] getEnigmaPanel() {
        return enigmaPanel;
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

    /**
     * El método premio se encarga de asignar al jugador su premio en función de la casilla en la que haya caído.
     * Si cae en una casilla con un número se le añade al jugador el dinero. Si el jugador cae en la casilla de
     * quiebra, pierde all su dinero. En caso de que caiga en la casilla de x2, duplica su dinero. Si cae en la
     * casilla de 1/2, da la mitad de su dinero a la banca. Al caer en la casilla de "pierde turno" y si el jugador
     * no tiene un comodín pierde su turno, si tiene un comodín no se pierde su turno y se le quita ese comodín al
     * jugador. Si cae en la casilla de comodín, se le añade un comodín.
     */
    @SuppressWarnings("GrazieInspection")
    public void Premio() {
        String premio = player.getCasillaRuleta();

        int premioInt = 0;
        int comodin = 0;
        try {
            premioInt = Integer.parseInt(premio);
        } catch (NumberFormatException e) {
            logger.info("Premio especial");
        }

        if (premioInt != 0) {
            player.setMoney(player.getMoney() + premioInt);
        }
        else {
            if (premio.equals("Quiebra")) {
                player.setMoney(0);
            }
            else if (premio.equals("x2")) {
                player.setMoney(player.getMoney() * 2);
            }
            else if (premio.equals("1/2")) {
                player.setMoney(player.getMoney() / 2);
            }
            else if (premio.equals("Comodín")) {
                comodin++;
            }
            else if (premio.equals("Pierde turno")) {
                boolean continuar = player.isTurn();
                if (comodin > 0) {
                    continuar = true;
                    comodin--;
                }
                else {
                    continuar = false;
                }
            }
        }


    }
}
