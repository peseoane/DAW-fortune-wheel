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

        Registers resultados = enigma.assertFraseCompatible(enigma);

        System.out.println("RA: " + resultados.RA);
        System.out.println("RB: " + resultados.RB);
        System.out.println("RC: " + resultados.RC);
        System.out.println("RD: " + resultados.RD);


    }

    private Registers assertFraseCompatible(Enigma enigma) {
        int RA = 0;
        int RB = 0;
        int RC = 0;
        int RD = 0;

        int RX = 0;
        int RZ = 0;

        for (String token : enigma.fraseTokenizada) {

            switch (RX) {
                case 0 -> {
                    if (++RZ + token.length() <= 12) {
                        RA++;
                        RZ += token.length() + 1;
                    } else {
                        RX++;
                        RZ = 0;
                    }
                }
                case 1 -> {
                    if (++RZ + token.length() <= 14) {
                        RB++;
                        RZ += token.length() + 1;
                    } else {
                        RX++;
                        RZ = 0;
                    }
                }
                case 2 -> {
                    if (++RZ + token.length() <= 14) {
                        RC++;
                        RZ += token.length() + 1;
                    } else {
                        RX++;
                        RZ = 0;
                    }
                }

                case 3 -> {
                    if (++RZ + token.length() <= 12) {
                        RD++;
                        RZ += token.length() + 1;
                    } else {
                        RX++;
                        RZ = 0;
                    }
                }
            }

            System.out.printf("Token: %s%n", token);
            System.out.println("this.RA: " + RA);
            System.out.println("this.RB: " + RB);
            System.out.println("this.RC: " + RC);
            System.out.println("this.RD: " + RD);
            System.out.println("this.RX: " + RX);
            System.out.println("this.RZ: " + RZ);
            System.out.println();

        }

        return new Registers(RA, RB, RC, RD);

    }
}
