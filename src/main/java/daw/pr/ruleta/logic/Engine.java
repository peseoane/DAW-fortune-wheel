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
    private final Player[] players;
    private final char[][] enigmaProgreso = new char[4][14];
    private boolean isGameFinished = false;
    private String pista;
    private Ruleta ruleta;
    private String pistaActual;
    private String frase;
    private char[][] enigmaPanel = new char[4][14];
    private boolean isPanelSolved = false;
    private int numeroPartida = 0;
    private int jugadorSeleccionado;

    public Engine() {

        int numeroJugadores = getNumeroJugadores();
        this.players = new Player[numeroJugadores];
        for (int i = 0; i < numeroJugadores; i++) {
            this.players[i] = registerPlayer();
            logger.info("Jugador " + i + " registrado" + players[i].getName() + " " + players[i].getMoney());
        }
        while (!isGameFinished) {
            logger.info("Jugando partida " + numeroPartida);
            jugarPartida(numeroJugadores);
            numeroPartida++;
            System.out.println("Desea continuar? (s/n)");
            String deseaContinuar = definitions.teclado.nextLine();
            if (deseaContinuar.equalsIgnoreCase("n")) {
                logger.info("El juego ha terminado");
                isGameFinished = true;
            }
            else {
                logger.info("Continuando partida");
            }
        }
    }

    private void jugarPartida(int numeroJugadores) {
        nuevoEnigmaYPista();
        this.pista = pistaActual;
        generarPanelsinResolver();
        ruleta = new Ruleta();
        if (numeroJugadores > 1) {
            jugadorSeleccionado = new java.util.Random().nextInt(numeroJugadores - 1);
        }
        generarPartida(jugadorSeleccionado);
        while (!getPanelSolved()) {
            if (jugadorSeleccionado == numeroJugadores - 1) {
                jugadorSeleccionado = 0;
            }
            else {
                jugadorSeleccionado++;
            }
            generarPartida(jugadorSeleccionado);
        }
        logger.info("El panel ha sido resuelto");
    }


    private void generarPanelsinResolver() {

        logger.info("Generando panel sin resolver");
        imprimirPanel(enigmaProgreso);
        sanitize(frase);

        // copiar enigmaPanel a enigmaProgreso

        for (int i = 0; i < enigmaPanel.length; i++) {
            for (int j = 0; j < enigmaPanel[i].length; j++) {
                // check if enigmaPanel[i][j] is a char
                if (Character.isLetter(enigmaPanel[i][j])) {
                    enigmaProgreso[i][j] = '_';
                }
                else {
                    enigmaProgreso[i][j] = '*';
                }
            }
        }
        logger.info("Panel sin resolver generado");
        imprimirPanel(enigmaProgreso);

    }

    public boolean getPanelSolved() {
        return isPanelSolved;
    }

    public boolean setPanelSolved(boolean panelSolved) {
        isPanelSolved = panelSolved;
        return isPanelSolved;
    }


    public boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }


    public void generarPartida(int posicionPlayer) {

        System.out.println("Turno de " + players[posicionPlayer].getName());
        System.out.println("Dinero: " + players[posicionPlayer].getMoney());
        System.out.println("Pista: " + pista);

        boolean status = true;

        while (status) {
            ruleta.girarRuleta();
            imprimirPanel(enigmaProgreso);
            String casilla = ruleta.getResultadoRuleta().toUpperCase();
            logger.info("La ruleta ha caído en " + casilla);
            System.out.println("La ruleta ha caído en " + casilla);
            logger.info("El enigma es" + frase);
            if (isNumeric(casilla)) {
                status = menuPremioNumerico(posicionPlayer, parseInt(casilla));
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha decidido " + status);
            }
            else {
                status = menuEspecial(posicionPlayer, casilla);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha decidido " + status);
            }
        }
        logger.info("El jugador " + players[posicionPlayer].getName() + " ha terminado su turno");
    }

    private boolean menuEspecial(int posicionPlayer, String casilla) {
        boolean continuar = true;
        switch (casilla) {
            case "QUIEBRA":
                players[posicionPlayer].setMoney(0);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha quedado en quiebra");
                continuar = false;
                break;
            case "PIERDE TURNO":
                if (players[posicionPlayer].getComodin() > 0) {
                    players[posicionPlayer].setComodin(players[posicionPlayer].getComodin() - 1);
                    logger.info("El jugador " + players[posicionPlayer].getName() + " ha usado un comodín");
                    continuar = true;
                }
                else {
                    logger.info("El jugador " + players[posicionPlayer].getName() + " ha perdido el turno por no " +
                                        "tener comodines");
                    continuar = false;
                }
                break;
            case "X2":
                players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() * 2);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha ganado el doble de dinero");
                continuar = false;
                break;
            case "1/2":
                players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() / 2);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha ganado la mitad de dinero");
                continuar = false;
                break;
            case "COMODÍN":
                players[posicionPlayer].setComodin(players[posicionPlayer].getComodin() + 1);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha ganado un comodín");
                continuar = true;
                break;
        }
        return continuar;
    }

    public boolean menuPremioNumerico(int posicionPlayer, int premio) {
        boolean status = true;
        System.out.println(frase);
        System.out.println("1. Resolver");
        if (players[posicionPlayer].getMoney() > 50) {
            System.out.println("2. Comprar vocal");
        }
        else {
            System.out.println("2. Comprar vocal (No tienes suficiente dinero)");
        }
        System.out.println("3. Probar letra " + premio);
        int eleccionJugador = definitions.teclado.nextInt();
        switch (eleccionJugador) {
            case 1:
                definitions.teclado.nextLine();
                System.out.println("Introduzca la frase");
                String fraseUser = definitions.teclado.nextLine();
                if (fraseUser.equalsIgnoreCase(frase)) {
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + premio);
                    logger.info("La frase se ha introducido correctamente");
                    setPanelSolved(true);
                    status = false;
                }
                else {
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() - premio);
                    logger.info("La frase se ha introducido incorrectamente");
                    status = true;
                }
                break;
            case 2:
                if (players[posicionPlayer].getMoney() < 50) {
                    System.out.println("No tienes suficiente dinero");
                    break;
                }
                else {
                    definitions.teclado.nextLine();
                    System.out.println("Introduzca la vocal");
                    char vocal = definitions.teclado.nextLine().charAt(0);
                    if (comprobarLetra(vocal) > 0) {
                        players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() - 50);
                        logger.info("La vocal se ha introducido correctamente");
                        status = false;
                    }
                    else {
                        players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + 50);
                        status = true;
                    }
                }
                break;
            case 3:
                System.out.println("Introduzca la letra");
                definitions.teclado.nextLine();
                char letra = definitions.teclado.nextLine().toUpperCase().charAt(0);
                if (comprobarLetra(letra) > 0) {
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + premio);
                    status = true;
                }
                else {
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney());
                    status = false;
                }
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        return status;
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
                    if (letra == 'a' || letra == 'e' || letra == 'i' || letra == 'o' || letra == 'u') {
                        System.err.println("Las vocales no se pueden revelar así");
                    }
                    else {
                        enigmaProgreso[i][j] = letra;
                        contador++;
                    }
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
        frase = sanitize(enigma.getFrase());
        pistaActual = enigma.getPista();
        enigmaPanel = enigma.getPanel();
        logger.info("Se ha creado un nuevo enigma");
        logger.info("Frase = " + frase);
        logger.info("Pista = " + pistaActual);
        logger.info("Panel = " + enigmaPanel);
    }

    private String sanitize(String fraseMala) {
        logger.info("Se va a sanear el input: ");
        String fraseBuena = fraseMala.toUpperCase();
        fraseBuena = fraseBuena.replaceAll("[ÁÀÂÄ]", "A");
        fraseBuena = fraseBuena.replaceAll("[ÉÈÊË]", "E");
        fraseBuena = fraseBuena.replaceAll("[ÍÌÎÏ]", "I");
        fraseBuena = fraseBuena.replaceAll("[ÓÒÔÖ]", "O");
        fraseBuena = fraseBuena.replaceAll("[ÚÙÛÜ]", "U");
        fraseBuena = fraseBuena.replaceAll("[.,;:]", "");
        fraseBuena = fraseBuena.replaceAll("[¿?¡!]", "");
        logger.info("Se ha sanado el input: " + fraseBuena);
        return fraseBuena;
    }


    public char[][] getEnigmaProgreso() {
        return enigmaProgreso;
    }


    public String getPista() {
        return pista;
    }

    public String getJugadorName(int posicionPlayer) {
        return players[posicionPlayer].getName();
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

    public void showConsonant() {
        System.out.println("Que consonante quieres revelar?");
        String consonant = definitions.teclado.nextLine();
        if (consonant.equalsIgnoreCase("a") || consonant.equalsIgnoreCase("e") || consonant.equalsIgnoreCase("i") || consonant.equalsIgnoreCase("o") || consonant.equalsIgnoreCase("u")) {
            System.err.println("Las vocales no se pueden revelar así");
        }
        else {
            for (int i = 0; i < enigmaProgreso.length; i++) {
                for (int j = 0; j < enigmaPanel.length; j++) {
                    if (enigmaPanel[i][j] == consonant.charAt(0)) {
                        enigmaProgreso[i][j] = enigmaPanel[i][j];
                        logger.info("La letra " + consonant + " se ha revelado");
                    }
                }
                logger.info("La letra " + consonant + " no se ha revelado");
            }
        }
    }

    /* Genera método para imprimir los paneles char[][] */
    public void imprimirPanel(char[][] panel) {
        for (int i = 0; i < panel.length; i++) {
            for (int j = 0; j < panel[i].length; j++) {
                System.out.print(panel[i][j] + " ");
            }
            System.out.println();
        }
    }
}