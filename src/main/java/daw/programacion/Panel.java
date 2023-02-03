package daw.programacion;

public class Panel {
    public static String[] phrases = {"CONFUSO, DUDOSO Y OSCURO", "SIN PLANES, SIN HORARIOS Y SIN MÓVILES"}; //"SIN PLANES, SIN HORARIOS Y SIN MÓVILES", "Ni muerta"
    public static char[] hidden = new char[phrases[1].length()];

    /**
     * El método esconde la frase escogida en la ronda y convierte todos los caracteres que tiene por asteríscos.
     * @return
     */
    public static char[] phraseToGuess() {
        for (int i = 0; i < phrases[1].length(); i++) {
            switch (phrases[1].charAt(i)) {
                case ',':
                case '.':
                case ' ':
                case '!':
                    hidden[i] = phrases[1].charAt(i);
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
    public static void showClue() {
        System.out.println("La frase son 3 sinónimos");
    }

    public static void showConsonant(char[] hidden) {
        System.out.println("Que consonante quieres revelar?");
        String consonant = definitions.teclado.nextLine().toUpperCase();
        if (consonant.equalsIgnoreCase("a") || consonant.equalsIgnoreCase("e") || consonant.equalsIgnoreCase("i") || consonant.equalsIgnoreCase("o") || consonant.equalsIgnoreCase("u")) {
            System.err.println("Las vocales no se pueden revelar así");
        } else {
            for (int i = 0; i < hidden.length; i++) {
                if (phrases[1].charAt(i) == consonant.charAt(0)) {
                    hidden[i] = phrases[1].charAt(i);
                }
            }
        }

        for (int i = 0; i < hidden.length; i++) {
            System.out.print(hidden[i]);
        }
    }

}
