package daw.programacion.logic.enigma.wrap;

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

    public String dbgString() {
        return "Casilla [dbChar=" + dbChar + ", isVisible=" + isVisible + ", isSolved=" + isSolved + "]";
    }
}