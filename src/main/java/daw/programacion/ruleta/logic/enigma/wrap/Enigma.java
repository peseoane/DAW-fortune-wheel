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
        for (String token : enigma.fraseTokenizada) {
            System.out.println(token);
        }

        MatrixR4Int resultados = enigma.assertFraseCompatible(enigma);

        System.out.println("RA: " + resultados.RA);
        System.out.println("RB: " + resultados.RB);
        System.out.println("RC: " + resultados.RC);
        System.out.println("RD: " + resultados.RD);


    }

    /**
     * Dado que el panel no es un array perfecto, comprobamos que cada palabra del enigma cabe tanto en longitud global
     * como por línea, si alguna palabra se corta, lanzamos excepción.
     * @param enigma
     * @return Retorno múltiple que contiene el array de palabras por cada línea.
     */
    private MatrixR4Int assertFraseCompatible(Enigma enigma) {
        int tokensPorLinea0 = 1;
        int tokensPorLinea1 = 1;
        int tokensPorLinea2 = 1;
        int tokensPorLinea3 = 1;

        int posicionLineaAnalizar = 0;
        int capacidadLocalLinea = 0;

        for (String token : enigma.fraseTokenizada) {

            switch (posicionLineaAnalizar) {
                case 0 -> {
                    if (++capacidadLocalLinea + token.length() <= 12) {
                        tokensPorLinea0++;
                        capacidadLocalLinea++;
                        capacidadLocalLinea += token.length();
                    } else {
                        posicionLineaAnalizar++;
                        capacidadLocalLinea = 0;
                    }
                }
                case 1 -> {
                    if (++capacidadLocalLinea + token.length() <= 14) {
                        tokensPorLinea1++;
                        capacidadLocalLinea++;
                        capacidadLocalLinea += token.length();
                    } else {
                        posicionLineaAnalizar++;
                        capacidadLocalLinea = 0;
                    }
                }
                case 2 -> {
                    if (++capacidadLocalLinea + token.length() <= 14) {
                        tokensPorLinea2++;
                        capacidadLocalLinea++;
                        capacidadLocalLinea += token.length();
                    } else {
                        posicionLineaAnalizar++;
                        capacidadLocalLinea = 0;
                    }
                }

                case 3 -> {
                    if (++capacidadLocalLinea + token.length() <= 12) {
                        tokensPorLinea3++;
                        capacidadLocalLinea++;
                        capacidadLocalLinea += token.length();
                    } else {
                        posicionLineaAnalizar++;
                        capacidadLocalLinea = 0;
                    }
                }
            }


            Logger logger = LogManager.getLogger(Main.class);
            logger.info("Estado tokenizador");
            logger.trace("Token: " + token);
            logger.trace("this.tokensPorLinea0: " + tokensPorLinea0);
            logger.trace("this.tokensPorLinea1: " + tokensPorLinea1);
            logger.trace("this.tokensPorLinea2: " + tokensPorLinea2);
            logger.trace("this.tokensPorLinea3: " + tokensPorLinea3);
            logger.trace("this.RX: " + posicionLineaAnalizar);
            logger.trace("this.capacidadLocalLinea: " + capacidadLocalLinea);

        }

        return new MatrixR4Int(tokensPorLinea0, tokensPorLinea1, tokensPorLinea2, tokensPorLinea3);

    }
}
