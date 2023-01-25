package daw.programacion;

public class Panel {

  // Por ahora pongo 1 frase, para poder probar el programa
  public static void phraseToGuess() {
    String[] phrases = {
      "Confuso, dudoso y oscuro",
      "Sin planes, sin horarios y sin móviles",
    };
    String replace = "";
    for (int i = 0; i < phrases.length; i++) {
      replace = phrases[i].replaceAll("[A-Za-z]", "*");
      System.out.println(replace);
    }
  }

  // Método para dar pistas sobre la frase que tenemos en esa ronda
  public static void showClue() {
    System.out.println("La frase son 3 sinónimos");
  }
}
