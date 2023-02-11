package daw.programacion.ruleta.logic.enigma.wrap;

import daw.programacion.ruleta.logic.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enigma {



    /*
    Un enigma está formado por un total de 12+14+14+12=52 casillas distribuidas de esa forma.
    Por tanto, hablamos de un array [[12],[14], [14], [12]] de casillas.
    Por cada array, debemos comprobar que no se corten los tokens de las palabras al pasarlos,
    si eso sucede, se genera una excepción.
     */

    private final int[][] Panel = {{12}, {14}, {14}, {12}};

    /*
    La consulta de SQL se pasa ya saneada, y se procesará
    para asegurarse que no se cortan las palabras cumpliendo la
    regla anteriormente mencionada de 12 14 14 12.
     */

    private final String[] fraseTokenizada;

    public Enigma(String stringResultFromSQLQuery) {
        fraseTokenizada = stringResultFromSQLQuery.split(" ");
    }

    public static void main(String[] args) {
        Enigma enigma = new Enigma("Despiden al profe con una cadena humana de aplausos");
        MatrixR4Int resultados = enigma.assertFraseCompatible(enigma);

    }

    /**
     * Dado que el panel no es un array perfecto, comprobamos que cada palabra del enigma cabe tanto en longitud global
     * como por línea, si alguna palabra se corta, lanzamos excepción.
     *
     * @param enigma
     * @return Retorno múltiple que contiene el array de palabras por cada línea.
     */
    private int[] assertFraseCompatible(Enigma enigma) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("-- CALCULANDO POSICIONES ENIGMA EN PANEL --");

        int[] filasTokenCapacidad = new int[4];
        int filaActual = 0;
        int filaAcumulador = 0;

        for (String token : enigma.fraseTokenizada) {
            int filaLongitud = (filaActual == 0 || filaActual == 3) ? 12 : 14;

            if (++filaAcumulador + token.length() > filaLongitud) {
                filaActual++;
                filaAcumulador = 0;
            }

            filasTokenCapacidad[filaActual]++;
            filaAcumulador += token.length();

            logger.debug(" @L" + filaActual + " ACU: " + filasTokenCapacidad[filaActual] + " len " + filaLongitud + " token: " + token);
        }
        return filasTokenCapacidad;
    }
}



