package daw.pr.ruleta.GUI;

import daw.pr.ruleta.Main;
import daw.pr.ruleta.SQL.SQLDriver;
import daw.pr.ruleta.logic.Ruleta;
import daw.pr.ruleta.struct.Enigma;
import daw.pr.ruleta.struct.Player;
import daw.pr.ruleta.struct.definitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static daw.pr.ruleta.GUI.App.ventanaGUI;
import static java.lang.Integer.parseInt;

public class EngineGUI {

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

    public EngineGUI() {

        int numeroJugadores = getNumeroJugadores();
        this.players = new Player[numeroJugadores];
        for (int i = 0; i < numeroJugadores; i++) {
            this.players[i] = registerPlayer();
            logger.info("Jugador " + i + " registrado" + players[i].getName() + " " + players[i].getMoney());
        }

        while (!isGameFinished) {
            for (Player eachPlayer : players) {
                eachPlayer.setMoney(eachPlayer.getMoneyAcumulado());
            }
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
                for (Player eachPlayer : players) {
                    // si dinero de each < 100 fijarlo a 100
                    if (eachPlayer.getMoney() < 100) {
                        eachPlayer.setMoneyAcumulado(100);
                    }
                }
            }
        }
    }

    private void jugarPartida(int numeroJugadores) {
        int jugadorSeleccionado = 0;
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


        boolean status = true;
        while (status) {
            ventanaGUI(players[posicionPlayer].getName(), players[posicionPlayer].getMoney(), pista, enigmaProgreso,
                       players[posicionPlayer].getComodin());
            reproducirSonidoGirarRuleta();
            ruleta.girarRuleta();
            String casilla = ruleta.getResultadoRuleta().toUpperCase();
            logger.info("La ruleta ha caído en " + casilla);
            logger.info("El enigma es " + frase);
            System.out.println("Turno de " + players[posicionPlayer].getName());
            System.out.println("Dinero: " + players[posicionPlayer].getMoney());
            System.out.println("Pista: " + pista);
            if (isNumeric(casilla)) {
                status = menuPremioNumerico(posicionPlayer, parseInt(casilla));
                logger.info("El jugador " + players[posicionPlayer].getName() + " su jugada ha sido: " + status);
            }
            else {
                status = menuEspecial(posicionPlayer, casilla);
                logger.info("El jugador " + players[posicionPlayer].getName() + " su jugada ha sido: " + status);
            }
        }

        logger.info("El jugador " + players[posicionPlayer].getName() + " ha terminado su turno");
    }

    private boolean menuEspecial(int posicionPlayer, String casilla) {
        boolean continuar = true;
        switch (casilla) {
            case "QUIEBRA":
                reproducirSonidoError();
                players[posicionPlayer].setMoney(0);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha quedado en quiebra");
                continuar = false;
                reproducirSonidoCambioTurno();
                break;
            case "PIERDE TURNO":
                reproducirSonidoError();
                if (players[posicionPlayer].getComodin() > 0) {
                    players[posicionPlayer].setComodin(players[posicionPlayer].getComodin() - 1);
                    logger.info("El jugador " + players[posicionPlayer].getName() + " ha usado un comodín");
                    reproducirSonidoFinalPanel();
                    continuar = true;
                }
                else {
                    logger.info("El jugador " + players[posicionPlayer].getName() + " ha perdido el turno por no " +
                                        "tener comodines");
                    continuar = false;
                    reproducirSonidoCambioTurno();
                }
                break;
            case "X2":
                reproducirSonidoFinalPanel();
                players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() * 2);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha ganado el doble de dinero");
                continuar = false;
                break;
            case "1/2":
                reproducirSonidoFinalPanel();
                players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() / 2);
                logger.info("El jugador " + players[posicionPlayer].getName() + " ha ganado la mitad de dinero");
                continuar = false;
                break;
            case "COMODÍN":
                reproducirSonidoFinalPanel();
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
                String fraseUser = definitions.teclado.nextLine().toUpperCase();
                if (fraseUser.equalsIgnoreCase(frase)) {
                    if (players[posicionPlayer].getMoneyAcumulado() < 50) {
                        players[posicionPlayer].setMoneyAcumulado(players[posicionPlayer].getMoney() + 100);
                    }
                    else {
                        players[posicionPlayer].setMoneyAcumulado(players[posicionPlayer].getMoney() + premio);
                    }
                    logger.info("La frase se ha introducido correctamente");
                    setPanelSolved(true);
                    for (int i = 0; i < enigmaPanel.length; i++) {
                        for (int j = 0; j < enigmaPanel[i].length; j++) {
                            enigmaProgreso[i][j] = enigmaPanel[i][j];
                            ventanaGUI(players[posicionPlayer].getName(), players[posicionPlayer].getMoney(), pista,
                                       enigmaProgreso, players[posicionPlayer].getComodin());
                            // Play a sound each time a letter is revealed
                            if (enigmaPanel[i][j] != '*') {
                                reproducirSonidoFinalPanel();
                            }


                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    status = false;
                    reproducirSonidoCambioTurno();
                }
                else {
                    logger.info("La frase se ha introducido incorrectamente");
                    reproducirSonidoError();
                    ventanaGUI(players[posicionPlayer].getName(), players[posicionPlayer].getMoney(), pista,
                               enigmaProgreso, players[posicionPlayer].getComodin());
                    status = false;
                    reproducirSonidoError();
                    reproducirSonidoCambioTurno();
                }
                break;
            case 2:
                if (players[posicionPlayer].getMoney() < 50) {
                    reproducirSonidoError();
                    System.out.println("No tienes suficiente dinero");
                    break;
                }
                else {
                    definitions.teclado.nextLine();
                    System.out.println("Introduzca la vocal");
                    char vocal = definitions.teclado.nextLine().charAt(0);
                    vocal = Character.toUpperCase(vocal);
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() - 50);

                    if (comprobarVocal(vocal)) {
                        reproducirSonidoFinalPanel();
                        logger.info("La vocal se ha introducido correctamente");
                        status = true;
                    }
                    else {
                        reproducirSonidoError();
                        logger.info("No existe la vocal!");
                        status = false;

                    }
                }
                break;


            case 3:
                System.out.println("Introduzca la letra");
                definitions.teclado.nextLine();
                char letra = definitions.teclado.nextLine().charAt(0);
                int contador = comprobarLetra(letra);
                if (contador > 0) {
                    logger.info("El premio total por la letra es: " + premio * contador + "ha habito " + contador +
                                        " veces");
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney() + premio * contador);
                    status = true;
                    reproducirSonidoFinalPanel();
                }
                else {
                    logger.info("La letra no existe");
                    reproducirSonidoError();
                    players[posicionPlayer].setMoney(players[posicionPlayer].getMoney());
                    status = false;
                }
                break;
            default:
                System.out.println("Opción no válida");
                reproducirSonidoError();
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
        if (Character.toUpperCase(letra) == 'A' || Character.toUpperCase(letra) == 'E' || Character.toUpperCase(letra) == 'I' || Character.toUpperCase(letra) == 'O' || Character.toUpperCase(letra) == 'U') {
            System.err.println("Las vocales no son permitidas.");
            contador = 0;
            reproducirSonidoError();
            reproducirSonidoCambioTurno();
        }
        else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    if (Character.toUpperCase(enigmaPanel[i][j]) == Character.toUpperCase(letra)) {
                        logger.info("enigmaPanel[" + i + "][" + j + "] = CHAR A COMPARAR ES: " + letra);
                        enigmaProgreso[i][j] = letra;
                        contador++;
                    }
                }
            }
        }
        reproducirSonidoFinalPanel();
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
        /* Preguntar a marta si hay que preguntar sobre el dinero
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
         */

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

    /**
     * Método para comprobar si el char que metió el usuario se encuentra en el panel. Si
     *
     * @param vocal
     * @return
     */

    public boolean comprobarVocal(char vocal) {
        // check that the vocal is in the char[][] enigmaPanel
        boolean status = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                if (Character.toUpperCase(enigmaPanel[i][j]) == vocal) {
                    System.out.println(enigmaPanel[i][j] + " = " + vocal);
                    logger.info("enigmaPanel[" + i + "][" + j + "] = VOCAL FOUND COINDICENCIA AT : " + i + j + vocal);
                    enigmaProgreso[i][j] = vocal;
                    status = true;
                }
            }
        }
        return status;
    }

    private void reproducirSonidoGirarRuleta() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("assets/girarRuleta.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logger.error("Error al reproducir el sonido de error");
        }
    }

    private void reproducirSonidoCambioTurno() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("assets/cambioTurno.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logger.error("Error al reproducir el sonido de error");
        }
    }

    private void reproducirSonidoFinalPanel() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("assets/ok.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logger.error("Error al reproducir el sonido de error");
        }
    }

    private void reproducirSonidoError() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("assets/error.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            logger.error("Error al reproducir el sonido de error");
        }
    }
}