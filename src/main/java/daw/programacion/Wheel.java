package daw.programacion;

public class Wheel {

  static final String[] ruleta = {
    "Quiebra",
    "x2",
    "100",
    "50",
    "100",
    "150",
    "Pierde turno",
    "150",
    "100",
    "50",
    "100",
    "50",
    "100",
    "200",
    "50",
    "150",
    "1/2",
    "50",
    "150",
    "100",
    "Comodín",
    "150",
    "100",
    "50",
    "100",
    "50",
    "100",
    "200",
  };

  static final String ruletaTemplate =
    """
                      |   %s  |    %s  |   %s  |
                      """;

  private static int calcularFuerza(int force) {
    // Aceptar force como seed de un generador de aleatorios entre 40 y 10

    return (int) (Math.random() * (40 - 12 + 1) + 12);
  }

  private static void wheelArrow(String StringLength) {
    for (int j = 0; j < StringLength.length(); j++) {
      if (j == StringLength.length() / 2) {
        System.out.print("↓");
      } else {
        System.out.print("_");
      }
    }
    System.out.println();
  }

  public static String ruleta(Player player) {
    // Vamos a crear un array con los posibles valores de la ruleta

    // Genera un entero aleatorio que esté comprendido entre 12 y 30.

    /* Leer en un bucle del array en cada pasada hay que leer n, n+1 y n+2, en caso de que n+1 o n+2 estén fuera
        de rango deben leerse desde la posición 0 del array. */

    int casillaInicio = 2;
    int i = 0;
    StringBuilder activeRuleta = new StringBuilder();
    i = casillaInicio;

    for (int j = 0; j < calcularFuerza(player.getForce()); j++) {
      if (i >= ruleta.length - 1) {
        System.out.println("I ES CERO");
        i = 0;
      } else {
        i++;
      }

      if (i == ruleta.length - 1) {
        String StringLength = String.format(
          ruletaTemplate,
          ruleta[i],
          ruleta[0],
          ruleta[1]
        );
        wheelArrow(StringLength);
        System.out.printf(ruletaTemplate, ruleta[i], ruleta[0], ruleta[1]);
        activeRuleta.setLength(0);
        activeRuleta.append(ruleta[0]);
      } else if (i == ruleta.length - 2) {
        String StringLength = String.format(
          ruletaTemplate,
          ruleta[i],
          ruleta[i + 1],
          ruleta[0]
        );
        wheelArrow(StringLength);
        System.out.printf(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[0]);
        activeRuleta.setLength(0);
        activeRuleta.append(ruleta[i + 1]);
      } else {
        // Print the length of the ruletaTemplate with the %s.
        String StringLength = String.format(
          ruletaTemplate,
          ruleta[i],
          ruleta[i + 1],
          ruleta[i + 2]
        );
        wheelArrow(StringLength);
        System.out.printf(
          ruletaTemplate,
          ruleta[i],
          ruleta[i + 1],
          ruleta[i + 2]
        );
        // Guardar ruleta[i+1] en activeRuleta, como es un stringbuffer antes borrar el contenido previo.
        activeRuleta.setLength(0);
        activeRuleta.append(ruleta[i + 1]);

        // Hacer un sleep del valor de j * 100
        try {
          Thread.sleep(j * 20L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    return activeRuleta.toString();
  }
}
