package daw.programacion;
import java.util.Scanner;

public class Player {

  private String name;
  private int money;
  private boolean turn;

  private int force;

  /**
   * En caso de que inicie el juego, el dinero empieza en 0
   * @param name nombre jugador
   */
  public Player(String name) {
    this.name = name;
    this.money = 0;
    this.force = (int) (Math.random() * (30 - 12 + 1) + 12);
  }

  /**
   * Constructor cuando un jugador mantiene dinero de otra ronda.
   * @param name nombre de jugador
   * @param money sobrecarga, en caso de tener dinero especificarlo
   */
  public Player(String name, int money) {
    this.name = name;
    this.money = money;
    this.force = (int) (Math.random() * (30 - 12 + 1) + 12);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMoney() {
    return money;
  }

  public int getForce() {
    return force;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public void buyVocal() {
    System.out.println("Que vocal quiere comprar?");
    String vocal = definitions.teclado.nextLine();
    if (money >= definitions.CANTIDAD_VOCAL) {
      switch(vocal.charAt(0)) {
        case 'a', 'e', 'i', 'o', 'u' -> money = money - definitions.CANTIDAD_VOCAL;
        default -> System.err.println("Introduce una vocal");
      }
    }
  }
  /**
   * Método para resolver panel
   */
  public static void resolvePanel() {
      System.out.println("¿Que frase crees que es el panel?");
      String solution = definitions.teclado.nextLine();
      if (Panel.phrases[0].equalsIgnoreCase(solution)) {
        System.out.println("Correcto");
      } else {
        System.out.println("No es correcto");
      }
    }
}
