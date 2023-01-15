package daw.programacion;

public class Wheel {

 public static void main(String[] args) {

    /**
     * Vamos a intentar dibujar una parte de la ruleta de la fortuna, no nos interesa manejar un canvas ni nada complejo
     * si no una ruleta simple con los diversos premios que hay en el juego.
     */

    // Vamos a crear un array con los posibles valores de la ruleta

    String[] ruleta = {
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
            "200"
    };

    String ruletaTemplate = """
                         ↓↓
            |   %s  |    %s  |   %s  |
            """;


        /* Leer en un bucle del array en cada pasada hay que leer n, n+1 y n+2, en caso de que n+1 o n+2 estén fuera
        de rango deben leerse desde la posición 0 del array. */

        for (int i = 0; i < ruleta.length; i++) {
            if (i == ruleta.length - 1) {
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[0], ruleta[1]);
            } else if (i == ruleta.length - 2) {
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[0]);
            } else {
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[i + 2]);
            }
        }

    }

}