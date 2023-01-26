package daw.programacion;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello, world!");
    String resultado = Conectar.conectar("SELECT * FROM enigmas;");
    System.out.println(resultado);
  }
}
