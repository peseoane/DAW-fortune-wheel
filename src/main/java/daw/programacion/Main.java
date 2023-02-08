package daw.programacion;

import static daw.programacion.Player.resolvePanel;

public class Main {

  public static void main(String[] args) {

    Panel panel = new Panel();
    Panel.showConsonant(Sqlite.obtenerEnigmaNuevo());
  }
}
