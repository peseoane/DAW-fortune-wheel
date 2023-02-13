package mejorado.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mejorado.logic.Panel;

public class Tablero extends Pane {

    private static final int ANCHO_CELDA = 50;
    private static final int ALTO_CELDA = 50;
    private static final int MARGEN = 10;

    public Tablero() {
        setPrefSize(4 * ANCHO_CELDA + 2 * MARGEN, 14 * ALTO_CELDA + 2 * MARGEN);
    }

    public void dibujarPanel(Panel panel) {
        getChildren().clear();
        for (int fila = 0; fila < panel.getPanelEnigma().length; fila++) {
            for (int columna = 0; columna < panel.getPanelEnigma()[fila].length; columna++) {
                Rectangle rectangulo = new Rectangle(columna * ANCHO_CELDA + MARGEN, fila * ALTO_CELDA + MARGEN,
                                                     ANCHO_CELDA, ALTO_CELDA);
                rectangulo.setFill(panel.getPanelEnigma()[fila][columna] == ' ' ? Color.WHITE : Color.BLACK);
                getChildren().add(rectangulo);
            }
        }
    }
}
