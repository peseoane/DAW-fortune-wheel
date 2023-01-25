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

  public void setMoney(int money) {
    this.money = money;
  }
  /*public int buyVocal() {
        if (money >= definitions.CANTIDAD_VOCAL) {
            if ()
        }
    }*/

  /* estructura de resolver panel
    public void resolvePanel() {
        //Probablemente mejor pasarlo a definitions
        Scanner teclado = new Scanner(System.in, "ISO-8859-1");
        String solution = teclado.nextLine();
        for (int i = 0; i < solution.length(); i++) {
        }
    }*/
}
