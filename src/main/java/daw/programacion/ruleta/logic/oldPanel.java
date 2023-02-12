package daw.programacion.ruleta.logic;

import daw.programacion.ruleta.sql.Connector;

public class oldPanel {
    public static char[] hidden;
    public static String phrases;

    public oldPanel() {
        phrases = Connector.obtenerEnigmaNuevo();
        Connector.darBajaEigma(phrases);
    }

    public static void showConsonant(char[] hidden) {
        System.out.println("Que consonante quieres revelar?");
        String consonant = HEADERS.teclado.nextLine().toUpperCase();
        if (consonant.equalsIgnoreCase("a") || consonant.equalsIgnoreCase("e") || consonant.equalsIgnoreCase("i") || consonant.equalsIgnoreCase("o") || consonant.equalsIgnoreCase("u")) {
            System.err.println("Las vocales no se pueden revelar así");
        }
        else {
            for (int i = 0; i < hidden.length; i++) {
                if (phrases.charAt(i) == consonant.charAt(0)) {
                    hidden[i] = phrases.charAt(i);
                }
            }
        }

        for (int i = 0; i < hidden.length; i++) {
            System.out.print(hidden[i]);
        }
    }

    /**
     * El método esconde la frase escogida en la ronda y convierte todos los caracteres que tiene por asteríscos.
     *
     * @return
     */
    public char[] phraseToGuess() {
        for (int i = 0; i < phrases.length(); i++) {
            switch (phrases.charAt(i)) {
                case ',':
                case '.':
                case ' ':
                case '!':
                    hidden[i] = phrases.charAt(i);
                    break;
                default:
                    hidden[i] = '*';
            }
        }

        for (int i = 0; i < hidden.length; i++) {
            System.out.print(hidden[i]);
        }

        System.out.println();
        return hidden;
    }

    // Método para dar pistas sobre la frase que tenemos en esa ronda
    public void showClue() {
        System.out.println("La frase son 3 sinónimos");
    }
}
