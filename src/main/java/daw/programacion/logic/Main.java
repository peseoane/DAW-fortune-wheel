package daw.programacion.logic;

import daw.programacion.sql.Connector;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {

    Panel panel = new Panel();
    String frase = Connector.conector(Connector.obtenerEnigmaNuevo());
    System.out.println(frase);
    Connector.conector(Connector.darBajaEigma(frase));
    // Panel.showConsonant();

  }
}
