package daw.pr.ruleta.GUI;

import daw.pr.ruleta.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class App {
    static Logger logger = LogManager.getLogger(Main.class);
    private static JFrame frame;

    public static void main(String[] args) {
        logger.info("Iniciando juego...");
        /*
        // mostrar la clase spalsh durante 3 segundos.
        splash splash = new splash();
        splash.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        EngineGUI engineGUI = new EngineGUI();
    }


    public static void actualizarVentana(String jugador, int dinero, String pista, char[][] panel) {
        // Actualizar el panel de información del jugador.
        JPanel playerInfoPanel = (JPanel) frame.getContentPane().getComponent(0);
        ((JLabel) playerInfoPanel.getComponent(1)).setText(jugador);
        ((JLabel) playerInfoPanel.getComponent(3)).setText(String.valueOf(dinero));

        // Actualizar el panel de botones.
        JButton[][] buttons = new JButton[4][14];
        JPanel panelContainer = (JPanel) frame.getContentPane().getComponent(1);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                buttons[i][j] = (JButton) panelContainer.getComponent(i * 14 + j);
                buttons[i][j] = (JButton) panelContainer.getComponent(i * 14 + j);
                buttons[i][j].setText(String.valueOf(panel[i][j]).toUpperCase());
                if (panel[i][j] == '*') {
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else {
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }

        // Actualizar la etiqueta de la pista.
        JPanel controlButtonPanel = (JPanel) frame.getContentPane().getComponent(2);
        ((JLabel) controlButtonPanel.getComponent(1)).setText(pista);
    }


    public static void ventanaGUI(String jugador, int dinero, String pista, char[][] panel, int comodin) {

        // Create a new JFrame
        if (frame == null) {
            frame = new JFrame("Panel");
        }
        else {
            frame.getContentPane().removeAll();
        }

        // Create a new JPanel to hold the player info.
        JPanel playerInfoPanel = new JPanel();
        // Set the layout to be a horizontal BoxLayout.
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.X_AXIS));
        // Add a label for the player name.
        playerInfoPanel.add(new JLabel("JUGADOR: "));
        playerInfoPanel.add(new JLabel(jugador)); // Replace "aquíLeyeraVariableJugador" with the
        // actual variable that stores the player name.
        // Add a label for the player's money.
        playerInfoPanel.add(new JLabel(" | DINERO: "));
        playerInfoPanel.add(new JLabel(String.valueOf(dinero))); // Replace "leerDinero()" with the actual

        playerInfoPanel.add(new JLabel(" | COMODINES: "));
        playerInfoPanel.add(new JLabel(String.valueOf(comodin))); // Replace "leerD
        // Add the player info panel to the frame.
        frame.getContentPane().add(playerInfoPanel, BorderLayout.NORTH);

        // Create a new 2D array of buttons.
        JButton[][] buttons = new JButton[4][14];
        // For each row...
        for (int i = 0; i < 4; i++) {
            // For each column...
            for (int j = 0; j < 14; j++) {
                // Create a new button with the character at that position.
                buttons[i][j] = new JButton(String.valueOf(panel[i][j]).toUpperCase());
                // If the character is a *...
                if (panel[i][j] == '*') {
                    // Set the background to blue.
                    buttons[i][j].setBackground(Color.BLUE);
                }
                // Otherwise...
                else {
                    // Set the background to white.
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }

        // Create a JPanel to hold the buttons.
        JPanel panelContainer = new JPanel();
        // Set the layout of the panel to be a 4x14 grid.
        panelContainer.setLayout(new GridLayout(4, 14));
        // For each row of buttons...
        for (int i = 0; i < 4; i++) {
            // For each column of buttons...
            for (int j = 0; j < 14; j++) {
                // Add the button at the current row and column to the panel.
                panelContainer.add(buttons[i][j]);
            }
        }

        // Add the panel to the frame.
        frame.getContentPane().add(panelContainer, BorderLayout.CENTER);

        // Create a new JPanel to hold the control buttons.
        JPanel controlButtonPanel = new JPanel();
        // Add a label for the control buttons.
        controlButtonPanel.add(new JLabel("ETIQUETA: "));
        controlButtonPanel.add(new JLabel("leerString")); // Replace "leerString" with the actual function call that
        // read pista and print in a JLabel
        controlButtonPanel.add(new JLabel(pista));

        // returns the string to be displayed in the label.
        // Add the "Comprar Vocal" button.
        JButton buyVowelButton = new JButton("Comprar Vocal");
        controlButtonPanel.add(buyVowelButton);
        // Add the "Tirar Ruleta" button.
        JButton spinButton = new JButton("Tirar Ruleta");
        controlButtonPanel.add(spinButton);

        // Add the "Resolver Panel" button.
        JButton solvePanelButton = new JButton("Resolver Panel");
        controlButtonPanel.add(solvePanelButton);

        // Add the control button panel to the frame.
        frame.getContentPane().add(controlButtonPanel, BorderLayout.SOUTH);

        // Set the size of the frame.
        frame.setSize(800, 600);

        // Make the frame visible.
        frame.setVisible(true);

        // Set the default close operation to exit the program.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to be resizable.

        frame.setResizable(true);
        frame.setVisible(true);
    }
}

