package daw.pr.ruleta.logic;

import daw.pr.ruleta.Main;
import daw.pr.ruleta.SQL.SQLDriver;
import daw.pr.ruleta.struct.Enigma;
import daw.pr.ruleta.struct.Player;
import daw.pr.ruleta.struct.definitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Integer.parseInt;

public class Engine {

    static Logger logger = LogManager.getLogger(Main.class);
    private final SQLDriver sql = new SQLDriver();
    private final Player player = new Player("test", 0);
    private final int turnPlayer = 0;
    private final char[][] enigmaProgreso = new char[4][14];

    private Player[] players;
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

    public void mostrarMenu(Player player, int premio) {

    }

    public void generarPartida(int posicionPlayer) {
        System.out.println("Turno de " + players[posicionPlayer].getName());
        boolean continuar = true;
        while (continuar) {
            System.out.println("Dinero: " + players[posicionPlayer].getMoney());
            System.out.println("Pista: " + pista);
            ruleta.girarRuleta();
            System.out.println("La ruleta ha caído en " + ruleta.getResultadoRuleta());
            switch (ruleta.getResultadoRuleta().toUpperCase()) {
                case "QUIEBRA":
                    players[posicionPlayer].setMoney(0);
                    break;
                case "PIERDE TURNO":
                    if (players[posicionPlayer].getComodin() > 0) {
                        continuar = true;
                        players[posicionPlayer].setComodin(players[posicionPlayer].getComodin() - 1);
                    }
                    else {
                        continuar = false;
                    }
                case "X2":
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() * 2);
                    break;
                case "1/2":
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() / 2);
                    break;
                case "COMODIN":
                    players[posicionPlayer].setComodin(players[posicionPlayer].getComodin() + 1);
                    break;
                default:
                    int premio = parseInt(ruleta.getResultadoRuleta());
                    int eleccionJugador = definitions.teclado.nextInt();

                    do {
                        System.out.println("1. Resolver");

                        if (players[posicionPlayer].getMoney() > 50) {
                            System.out.println("2. Comprar vocal");
                        }
                        else {
                            System.out.println("2. Comprar vocal (No tienes suficiente dinero)");
                        }
                        System.out.println("3. Probar letra " + premio);
                        switch (eleccionJugador) {
                            case 1:
                                System.out.println("Introduzca la frase");
                                String frase = definitions.teclado.nextLine();
                                if (frase.equals(enigmaFrase)) {
                                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + premio);
                                }
                                break;
                            case 2:
                                if (players[posicionPlayer].getMoney() < 50) {
                                    System.out.println("No tienes suficiente dinero");
                                    break;
                                }
                                else {
                                    System.out.println("Introduzca la vocal");
                                    char vocal = definitions.teclado.nextLine().charAt(0);
                                    if (comprobarLetra(vocal) > 0) {
                                        players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() - 50);
                                    }
                                    else {
                                        players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + 50);
                                    }
                                }
                                break;
                            case 3:
                                System.out.println("Introduzca la letra");
                                char letra = definitions.teclado.nextLine().charAt(0);
                                if (comprobarLetra(letra) > 0) {
                                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + premio);
                                }
                                else {
                                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() - premio);
                                }
                                break;
                            default:
                                System.out.println("Opción no válida");
                                break;
                        }
                    } while (!continuar);
                    break;
            }
        }

    }

    /*
    Este método recibe un char por argumento y comprueba que esté presente en char[][] enigmaPanel, si lo está,
    copiar ese char a char[][] enigmaProgreso en la misma posición que en char[][] enigmaPanel
    y devolver el número de veces que aparece ese char en char[][] enigmaPanel
     */

    public int comprobarLetra(char letra) {
        int contador = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                if (enigmaPanel[i][j] == letra) {
                    enigmaProgreso[i][j] = letra;
                    contador++;
                }
            }
        }
        return contador;
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
            return true;
        }
        else {
            System.out.println("Respuesta incorrecta");
            return false;
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