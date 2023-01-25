package daw.programacion;

public class Wheel {

    public static void main(String[] args) {
        // Vamos a crear un array con los posibles valores de la ruleta
        tablaMultiplicar();
        String[] ruleta = {"Quiebra", "x2", "100", "50", "100", "150", "Pierde turno", "150", "100", "50", "100", "50", "100", "200", "50", "150", "1/2", "50", "150", "100", "Comodín", "150", "100", "50", "100", "50", "100", "200",};

        String ruletaTemplate = """
                |   %s  |    %s  |   %s  |
                """;

    /* Leer en un bucle del array en cada pasada hay que leer n, n+1 y n+2, en caso de que n+1 o n+2 estén fuera
        de rango deben leerse desde la posición 0 del array. */

        for (int i = 0; i < ruleta.length; i++) {
            if (i == ruleta.length - 1) {
                String StringLength = String.format(ruletaTemplate, ruleta[i], ruleta[0], ruleta[1]);
                wheelArrow(StringLength);
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[0], ruleta[1]);
            } else if (i == ruleta.length - 2) {
                String StringLength = String.format(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[0]);
                wheelArrow(StringLength);
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[0]);
            } else {
                // Print the length of the ruletaTemplate with the %s.
                String StringLength = String.format(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[i + 2]);
                wheelArrow(StringLength);
                System.out.printf(ruletaTemplate, ruleta[i], ruleta[i + 1], ruleta[i + 2]);
            }
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Usado para mostrar las flechas más o menos centradas y que apunten al premio que corresponde.
     *
     * @param StringLength recibe un entero con la longitud de 3 ruletas ya completadas con sus valores
     */
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

    // Metodo que muestre todas las tablas de multiplicar del 1 al 10 en arrays

    public static void tablaMultiplicar() {
        int[][] tabla = new int[10][10];
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                tabla[i][j] = (i + 1) * (j + 1);
            }
        }
        for (int[] ints : tabla) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}
