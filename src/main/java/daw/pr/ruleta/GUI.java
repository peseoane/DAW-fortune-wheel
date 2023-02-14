package daw.pr.ruleta;

import daw.pr.ruleta.struct.Enigma;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static void main(String[] args) {
        Enigma mi = new Enigma("Hola que tal");
        char[][] panelArray = mi.getPanel();

        JFrame frame = new JFrame("Panel");

        JButton[][] buttons = new JButton[4][14];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                buttons[i][j] = new JButton(String.valueOf(panelArray[i][j]));
                if (panelArray[i][j] == '*') {
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else {
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayout(4, 14));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                panelContainer.add(buttons[i][j]);
            }
        }
        frame.getContentPane().add(panelContainer);

        frame.pack();
        frame.setVisible(true);
    }
}
