package daw.programacion;

import static daw.programacion.Player.resolvePanel;

public class Main {

  public static void main(String[] args) {

    Panel panel = new Panel();
    String frase = Sqlite.conector(Sqlite.obtenerEnigmaNuevo());
    System.out.println(frase);
    Sqlite.conector(Sqlite.darBajaEigma(frase));
    // Panel.showConsonant();
  }
}
