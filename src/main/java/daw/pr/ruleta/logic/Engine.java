package daw.pr.ruleta.logic;

import daw.pr.ruleta.Main;
import daw.pr.ruleta.SQL.SQLDriver;
import daw.pr.ruleta.struct.Enigma;
import daw.pr.ruleta.struct.Player;
import daw.pr.ruleta.struct.definitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Engine {

    static Logger logger = LogManager.getLogger(Main.class);
    private final SQLDriver sql = new SQLDriver();
    private final Player player = new Player("test", 0);
    private final int turnPlayer = 0;
    private final char[][] enigmaProgreso = new char[4][14];
    ArrayList<Player> players = new ArrayList<Player>();
    private String pista;
    private Ruleta ruleta;
    private char[][] enigmaPanel = new char[4][14];
    private String pistaActual;
    private String enigmaFrase;

    public Engine() {
        int numeroJugadores = getNumeroJugadores();
        for (int i = 0; i < numeroJugadores; i++) {
            this.players.add(registerPlayer());
            logger.info("Jugador " + i + " registrado" + players.get(i).getName() + " " + players.get(i).getMoney());
        }
        nuevoEnigmaYPista();
        this.pista = pistaActual;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                enigmaProgreso[i][j] = '*';
            }
        }
        ruleta = new Ruleta();
    }

    public Engine(ArrayList players) {
        this.players = players;
        nuevoEnigmaYPista();
    }

    public void mostrarMenu() {
        do {
            System.out.println("1. Generar partida");
            System.out.println("2. Resolver");
            if (player.getMoney() > 50) {
                System.out.println("3. Comprar vocal");
            }
            else {
                System.out.println("3. Comprar vocal (No tienes suficiente dinero)");
            }
            System.out.println("4. Salir");
        } while ()
    }

    public void generarPartida(int jugador) {

    }

    public int getNumeroJugadores() {
        int numeroJugadores = 0;
        Boolean flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca el número de jugadores");
                numeroJugadores = definitions.teclado.nextInt();
                definitions.teclado.nextLine();
                if (numeroJugadores < 1 || numeroJugadores > 4) {
                    throw new Exception();
                }
                flag = false;
                logger.info("El número de jugadores introducido es válido");
            } catch (Exception e) {
                logger.error("El número de jugadores introducido no es válido");
            }
        }
        return numeroJugadores;
    }

    // Método para seleccionar una posicin del arrayList

    private Player registerPlayer() {
        String name = null;
        double money = 0;
        Boolean flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca su nombre");
                name = definitions.teclado.nextLine();
                flag = false;
                logger.info("El nombre introducido es válido");
            } catch (Exception e) {
                logger.error("El nombre introducido no es válido");
                definitions.teclado.nextLine();
            }
        }
        flag = true;
        while (flag) {
            try {
                System.out.println("Introduzca su dinero");
                money = definitions.teclado.nextDouble();
                definitions.teclado.nextLine();
                if (money < 0) {
                    throw new Exception();
                }
                flag = false;
                logger.info("El dinero introducido es válido");
            } catch (Exception e) {
                logger.error("El dinero introducido no es válido");
                definitions.teclado.nextLine();
            }
        }

        logger.info("Nombre = " + name + " Dinero " + money);
        return new Player(name, (int) (money));
    }

    public void nuevoEnigmaYPista() {
        Enigma enigma = new Enigma(new SQLDriver());
        this.enigmaFrase = enigma.getEnigma();
        this.pistaActual = enigma.getPista();
        this.enigmaPanel = enigma.getPanel();
    }


    public Player getJugador() {
        return this.players.get(turnPlayer);
    }

    public char[][] getEnigmaProgreso() {
        return enigmaProgreso;
    }

    public ArrayList getPlayers() {
        return players;
    }

    public String getPistaActual() {
        return pistaActual;
    }

    public void setPistaActual(String pistaActual) {
        this.pistaActual = pistaActual;
    }

    public String getEnigmaFrase() {
        return enigmaFrase;
    }

    public void setEnigmaFrase(String enigmaFrase) {
        this.enigmaFrase = enigmaFrase;
    }

    public String getPista() {
        return pista;
    }

    public String getJugadorName() {
        return player.getName();
    }


    /*
    Crear un método que pregunte al usuario cuantos jugadores y los añada a un arraylist players
    debe ser 1 y 4 posibles.
     */

    public char[][] getEnigmaPanel() {
        return enigmaPanel;
    }

    public void setEnigmaPanel(char[][] enigmaPanel) {
        this.enigmaPanel = enigmaPanel;
    }

    public boolean intentarResolverPanel() {
        System.out.println("Introduzca la respuesta");
        String respuesta = definitions.teclado.nextLine();
        if (respuesta.equals(this.enigmaFrase)) {
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
        String premio = ruleta.girarRuleta();

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

    public void showConsonant() {
        System.out.println("Que consonante quieres revelar?");
        String consonant = definitions.teclado.nextLine();
        if (consonant.equalsIgnoreCase("a") || consonant.equalsIgnoreCase("e") || consonant.equalsIgnoreCase("i") || consonant.equalsIgnoreCase("o") || consonant.equalsIgnoreCase("u")) {
            System.err.println("Las vocales no se pueden revelar así");
        }
        else {
            for (int i = 0; i < enigmaProgreso.length; i++) {
                if (enigmaPanel[i].equals(consonant.charAt(0))) {
                    enigmaProgreso[i] = enigmaPanel[i];
                    logger.info("La letra " + consonant + " se ha revelado");
                }
                logger.info("La letra " + consonant + " no se ha revelado");
            }
        }
    }

    /* Genera método para imprimir los paneles char[][] */
    public void imprimirPanel(char[][] panel) {
        for (int i = 0; i < panel.length; i++) {
            System.out.print(panel[i] + " ");
        }
        System.out.println();
    }
}
