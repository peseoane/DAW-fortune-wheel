package daw.programacion.ruleta.logic.enigma;

public class Casilla {

    private char dbChar;
    private boolean isVisible;
    private boolean isSolved;

    public Casilla(char dbChar) {
        this.dbChar = dbChar;
        this.isVisible = false;
        this.isSolved = false;
    }

    public char getDbChar() {
        if (isVisible) {
            return dbChar;
        } else {
            return '*';
        }
    }

    public void hacerVisibleCasilla(){
        this.isVisible = true;
    }

}