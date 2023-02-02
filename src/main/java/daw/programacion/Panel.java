package daw.programacion;

public class Panel {
    public static String[] phrases = {"Confuso, dudoso y oscuro"}; //"Sin planes, sin horarios y sin móviles", "Ni muerta"
    public static char[][] hidden = new char[phrases.length][];

    // Por ahora pongo 1 frase, para poder probar el programa
    public static char[][] phraseToGuess() {
        hidden = new char[phrases.length][];
        for (int i = 0; i < phrases.length; i++) {
            hidden[i] = new char[phrases[i].length()];
            for (int j = 0; j < phrases[i].length(); j++) {
                switch (phrases[i].charAt(j)) {
                    case ',' -> hidden[i][j] = phrases[i].charAt(j);
                    case '.' -> hidden[i][j] = phrases[i].charAt(j);
                    case ' ' -> hidden[i][j] = phrases[i].charAt(j);
                    case '!' -> hidden[i][j] = phrases[i].charAt(j);
                    default -> hidden[i][j] = '*';
                }
            }
        }
        for (int i = 0; i < hidden.length; i++) {
            for (int j = 0; j < hidden[i].length; j++) {
                System.out.print(hidden[i][j]);
            }
        }
        System.out.println();
        return hidden;
    }

    // Método para dar pistas sobre la frase que tenemos en esa ronda
    public static void showClue() {
        System.out.println("La frase son 3 sinónimos");
    }
}
