package daw.programacion;

public class Panel {

  // Por ahora pongo 1 frase, para poder probar el programa
  public static void phraseToGuess() {
    String[] phrases = { "Confuso, dudoso y oscuro" };
  }

  // Método para dar pistas sobre la frase que tenemos en esa ronda
  public static void showClue() {
    System.out.println("La frase son 3 sinónimos");
  }
}
