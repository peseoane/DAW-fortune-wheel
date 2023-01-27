package daw.programacion;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello, world!");
    String resultado = Sqlite.conector(Sqlite.queryNoContestada());
    Sqlite.conector(Sqlite.anularQuery(resultado));
    System.out.println(resultado);
  }
}
