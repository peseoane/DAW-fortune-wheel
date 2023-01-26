package daw.programacion;

public class Panel {
  static String[] frases = {"Confuso, dudoso y oscuro"}; //, "Sin planes, sin horarios y sin móviles"

  // Por ahora pongo 1 frase, para poder probar el programa
  public static String phraseToGuess() {
    String replace = "";
    for (int i = 0; i < frases.length; i++) {
      replace = frases[i].replaceAll("[A-Za-z]", "*");
      System.out.println(replace);
    }
    return replace;
  }

  // Método para dar pistas sobre la frase que tenemos en esa ronda
  public static void showClue() {
    System.out.println("La frase son 3 sinónimos");
  }
}
