package daw.programacion.logic.enigma.wrap;

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
        Enigma enigma = new Enigma("Hola que tal estas");
        for (String token : enigma.fraseTokenizada) {
            System.out.println(token);
        }

        Resultados resultados = enigma.assertFraseCompatible(enigma);

        System.out.println("RA: " + resultados.RA);
        System.out.println("RB: " + resultados.RB);
        System.out.println("RC: " + resultados.RC);
        System.out.println("RD: " + resultados.RD);


    }

    private Resultados assertFraseCompatible(Enigma enigma) {
        int RA = 0;
        int RB = 0;
        int RC = 0;
        int RD = 0;

        int RX = 0;
        int RZ = 0;

        for (String token : enigma.fraseTokenizada) {
            switch (RX) {
                case 0 -> {
                    if (RA + token.length() <= 12) {
                        RA++;
                    } else {
                        RX++;
                    }
                } case 1 -> {
                    if (RB + token.length() <= 14) {
                        RB++;
                    } else  {
                        RX++;
                    }
                } case 2 -> {
                    if (RC + token.length() <= 14) {
                        RC++;
                    } else  {
                        RX++;
                    }
                }

                case 3 -> {
                    if (RC + token.length() <= 12) {
                        RD++;
                    } else  {
                        RX++;
                    }
                }
            }
        }

        return new Resultados(RA, RB, RC, RD);

    }

}
