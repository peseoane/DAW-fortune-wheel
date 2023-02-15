package daw.pr.ruleta;

import daw.pr.ruleta.logic.Engine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class GUI {

    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        for (int w = 0; w < 4; w++) {
            Engine engine = new Engine();

            // Get the panel array from the Enigma object
            char[][] panelArray = engine.getEnigmaPanel();
            // Create a new JFrame
            JFrame frame = new JFrame("Panel");

            // Create a new 2D array of buttons
            JButton[][] buttons = new JButton[4][14];
            // For each row
            for (int i = 0; i < 4; i++) {
                // For each column
                for (int j = 0; j < 14; j++) {
                    // Create a new button with the character at that position
                    buttons[i][j] = new JButton(String.valueOf(panelArray[i][j]));
                    // If the character is a *
                    if (panelArray[i][j] == '*') {
                        // Set the background to blue
                        buttons[i][j].setBackground(Color.BLUE);
                    }
                    // Otherwise
                    else {
                        // Set the background to white
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
            frame.getContentPane().add(panelContainer);
            // Pack the frame to the size of the buttons.
            frame.pack();
            // Make the frame visible.
            frame.setVisible(true);
        }

    }
}
