package daw.pr.ruleta.struct;

import daw.pr.ruleta.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enigma {

    static Logger logger = LogManager.getLogger(Main.class);
    private final char[][] panelEnigma;

    /**
     * Un enigma está formado por un total de 12+14+14+12=52 casillas distribuidas de esa forma.
     * Por tanto, hablamos de un array [[12],[14], [14], [12]] de casillas.
     * Por cada array, debemos comprobar que no se corten los tokens de las palabras al pasarlos,
     * Dado que el panel no es un array perfecto, comprobamos que cada palabra del Enigma cabe tanto en longitud global
     * como por línea, si alguna palabra se corta, lanzamos excepción.
     *
     * @param enigma Acepta una frase en formato texto plano desde el SQL, se supone que ya compatibles.
     */
    public Enigma(String enigma) {
        String[] enigmaTokenizado = tokenizarFrase(enigma);
        panelEnigma = cuantosTokensPorLinea(enigmaTokenizado);
    }

    private String[] tokenizarFrase(String enigma) {
        if (enigma.length() > 52) {
            logger.error("El enigma no puede tener más de 52 caracteres.");
            throw new IllegalArgumentException("El enigma no puede tener más de 52 caracteres.");
        }

        return enigma.split(" ");
    }

    private char[][] cuantosTokensPorLinea(String[] fraseTokenizada) {
        logger.info("-- CALCULANDO POSICIONES ENIGMA EN PANEL --");

        // Nuestro panel es de la televisión no es un rectángulo perfecto!
        char[][] panel = new char[4][14];
        for (int i = 0; i < panel.length; i++) {
            for (int j = 0; j < panel[0].length; j++) {
                panel[i][j] = '*';
            }
        }

        int end = 13;
        int row = 0;
        int col = 1;
        for (String token : fraseTokenizada) {
            // Dado que el panel no es un rectángulo perfecto hay que ver si dejar el asterisco o no de las esquinas
            if (token.length() > end - col) {
                row++;
                col = (row == 3) ? 1 : 0;
            }

            // Añadimos por cada n letra al array
            for (char c : token.toCharArray()) {
                panel[row][col++] = c;
                logger.info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + c + " TOKEN: " + token);
            }

            // Añadir un espacio entre palabras si no es la columna final
            if (col < end) {
                panel[row][col++] = ' ';
                logger.info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + ' ' + " TOKEN: " + token);
            }
        }
        return panel;
    }

    public static void main(String[] args) {
        Enigma enigma = new Enigma("Hola que tal");
        logger.info(enigma.getPanelEnigma());
    }

    public String getPanelEnigma() {
        StringBuilder sb = new StringBuilder();
        for (char[] linea : panelEnigma) {
            sb.append("\n");
            for (int j = 0; j < panelEnigma[0].length; j++) {
                // Añadir a un string builder cada linea
                sb.append(linea[j]);
            }
        }

        return sb.toString();
    }

    public char[][] getPanel() {
        return panelEnigma;
    }

    public String getEnigma() {
        return getPanelEnigma();
    }

}