package Domini;

import java.util.Vector;

public interface MapaI {
    String getID();
    String[][] getMatrix();
    Vector<String> getNumerosExistents();
    Vector<Integer> getNumerosRestants();
    char getTipo();
    String getAngulos();
    int getFilas();
    int getColumnas();
    int getInterrogants();
    boolean teSolucio();
    void setSolucio(boolean solucio);
    void setMatrix(String[][] matrix);
    Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);
    boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);
    String[][] hidatoValido();
}
