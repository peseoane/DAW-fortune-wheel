package mejorado.logic;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public interface HEADERS {
    int CANTIDAD_VOCAL = 50;
    Scanner teclado = new Scanner(System.in, StandardCharsets.ISO_8859_1);
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
            "200",
    };
    int CANTIDAD_ENIGMA = 150;
}
