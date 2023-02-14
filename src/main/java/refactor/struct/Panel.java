package refactor.struct;


import mejorado.logic.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Panel {

    static Logger logger = LogManager.getLogger(Main.class);
    private final char[][] panelEnigma;
    private final String enigma;

    /**
     * Un enigma está formado por un total de 12+14+14+12=52 casillas distribuidas de esa forma.
     * Por tanto, hablamos de un array [[12],[14], [14], [12]] de casillas.
     * Por cada array, debemos comprobar que no se corten los tokens de las palabras al pasarlos,
     * Dado que el panel no es un array perfecto, comprobamos que cada palabra del Panel cabe tanto en longitud global
     * como por línea, si alguna palabra se corta, lanzamos excepción.
     *
     * @param enigma Acepta una frase en formato texto plano desde el SQL, se supone que ya compatibles.
     */
    public Panel(String enigma) {
        this.enigma = enigma;
        String[] enigmaTokenizado = tokenizarFrase(enigma);
        panelEnigma = cuantosTokensPorLinea(enigmaTokenizado);
    }

    private String[] tokenizarFrase(String enigma) {
        if (enigma.length() > 52) {
            getLogger().error("El enigma no puede tener más de 52 caracteres.");
            throw new IllegalArgumentException("El enigma no puede tener más de 52 caracteres.");
        }

        return enigma.split(" ");
    }

    // Reescribir el metodo de imprimir para que panelEnigma

    private char[][] cuantosTokensPorLinea(String[] fraseTokenizada) {
        getLogger().info("-- CALCULANDO POSICIONES ENIGMA EN PANEL --");

        // Estamos usando el panel de la tele, no es rectangular perfecto.
        char[][] panel = new char[4][14];

        panel[0][0] = '*';
        panel[3][0] = '*';
        panel[0][13] = '*';
        panel[3][13] = '*';

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
                getLogger().info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + c + " TOKEN: " + token);
            }

            // Añadir un espacio entre palabras si no es la columna final
            if (col < end) {
                panel[row][col++] = ' ';
                getLogger().info("ROW: " + row + " COLUMNA: " + col + " CHAR: " + ' ' + " TOKEN: " + token);
            }
        }
        return panel;
    }

    static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        mejorado.logic.Panel panel = new mejorado.logic.Panel("Despiden al profe con una cadena humana de aplausos");
    }

    public void toLog(char[][] panelEnigma) {
        for (char[] linea : panelEnigma) {
            for (int j = 0; j < panelEnigma.length; j++) {
                // Añadir a un string builder cada linea
                logger.info(linea[j]);
            }
        }
    }

    public char[][] getPanelEnigma() {
        logger.info("Se ha solicitado el panel enigma, PASANDO POR REFERENCIA");
        return panelEnigma;
    }

    /**
     * Imprime el panel enigma en formato texto plano y no su referencia en memoria.
     *
     * @return Devuelve el panel enigma en formato texto plano.
     */

    /*
    @Override
    public String toString() {
        return getEnigma();
    }*/
    public String getEnigma() {
        return enigma;
    }
}