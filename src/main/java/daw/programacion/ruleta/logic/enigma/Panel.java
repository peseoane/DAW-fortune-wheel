package daw.programacion.ruleta.logic.enigma;

import daw.programacion.ruleta.logic.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Panel {

    private final String enigma;
    private final char[][] canvasEnigma;
    private final String[] enigmaTokenizado;
    /**
     * Un enigma está formado por un total de 12+14+14+12=52 casillas distribuidas de esa forma.
     * Por tanto, hablamos de un array [[12],[14], [14], [12]] de casillas.
     * Por cada array, debemos comprobar que no se corten los tokens de las palabras al pasarlos,
     * <p>
     * Dado que el panel no es un array perfecto, comprobamos que cada palabra del Panel cabe tanto en longitud global
     * como por línea, si alguna palabra se corta, lanzamos excepción.
     *
     * @param enigma Acepta una frase en formato texto plano desde el SQL, se supone que ya compatibles.
     * @return Retorno múltiple que contiene el array de palabras por cada línea.
     */
    Logger logger = LogManager.getLogger(Main.class);
    private int PanelCanvas;

    public Panel(String enigma) {
        this.enigma = enigma;
        this.enigmaTokenizado = tokenizarFrase(enigma);
        this.canvasEnigma = cuantosTokensPorLinea(enigmaTokenizado);
    }

    public static void main(String[] args) {
        Panel panel = new Panel("Despiden al profe con una cadena humana de aplausos");
        System.out.println(panel.PanelCanvas);
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

        char[][] canvas = new char[4][14];
        canvas[0][0] = '*';
        canvas[3][0] = '*';
        canvas[0][13] = '*';
        canvas[3][13] = '*';

        int start;
        int end;
        int col = 0;
        int row = 0;

        for (String token : fraseTokenizada) {
            if (row == 0 || row == 3) {
                start = 1;
                end = 12;
            } else {
                start = 0;
                end = 13;
            }

            if (token.length() > end - col) {
                row++;
                col = 0;
            }

            for (char c : token.toCharArray()) {
                canvas[row][col] = c;
                col++;
                // Imprimir en logger.info por orden ROW COLUMNA CHAR TOKEN
                logger.info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + c + " TOKEN: " + token);
            }

            if (col < end) {
                canvas[row][col++] = ' ';
                logger.info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + ' ' + " TOKEN: " + token);
            }

        }

        logger.info("-- FIN CALCULANDO POSICIONES ENIGMA EN PANEL --");
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[i].length; j++) {
                StringBuilder panelDebug = new StringBuilder().append(canvas[i][j]);}
            System.out.println();
        }
        return canvas;
    }
}



