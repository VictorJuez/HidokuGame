package Domini;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.UUID;
import java.util.Vector;

public class Tablero extends Mapa {
    private boolean adyacenciaAngulos;
    private static int filas;
    private static int columnas;
    private static String[][] matrix;

    private Tablero(){
        //Not allowed
    }



    public static Tablero createTablero(int filas, int columnas, String[][] tab){
        Tablero t = new Tablero();
        t.filas = filas;
        t.columnas = columnas;
        t.matrix = tab;
        t.ID = UUID.randomUUID().toString();
        instances.add(t.ID);
        hidatoValido();
        return t;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public static void hidatoValido(){
        String[][] A = matrix;
        Vector<Integer> v;
        v = numerosRestants();
        backtrackingResolucio(0,A, v);
    }

    private static void backtrackingResolucio(int k, String[][] A, Vector v){
        if(k>=filas*columnas) return;
        int x = k/columnas;
        int y = k%columnas;
        System.out.println(v);
        if(v.size() == 0) {
            processa(A);
        }
        else{
            if(A[x][y].equals("?")){
                int aux = (int) v.get(0);
                if(posicioCorrecte(x,y,A, aux, v)) {
                    A[x][y] = String.valueOf(aux);
                    v.remove(0);
                    backtrackingResolucio(0, A ,v);
                    v.add(0,aux);
                    A[x][y] = "?";
                }
                else backtrackingResolucio(k+1,A,v);
            }
            else {
                backtrackingResolucio(k+1,A,v);
            }
        }
    }

    private static boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v){
        boolean adjacentPetit = false;
        boolean adjacentGran = false;
        boolean adjacentInterrogant = false;
        for(int i=0; i<4; ++i){
            int xx = x;
            int yy = y;
            switch (i){
                case 0:
                    xx=x;
                    yy=y-1;
                    break;
                case 1:
                    xx=x-1;
                    yy=y;
                    break;
                case 2:
                    xx=x;
                    yy=y+1;
                    break;
                case 3:
                    xx=x+1;
                    yy=y;
                    break;
            }
            if(xx>=0 && yy>=0 && xx<A.length && yy<A[0].length) {

                if (isInteger(A[xx][yy])) {
                    int tableValue = Integer.parseInt(A[xx][yy]);

                    if (tableValue == toInsert - 1) adjacentPetit = true;
                    if (tableValue == toInsert + 1) adjacentGran = true;
                } else if (A[xx][yy].equals("?")) adjacentInterrogant = true;
            }
        }

        if(adjacentGran && adjacentPetit) return true;
        if(adjacentGran && adjacentInterrogant) if(v.contains(toInsert-1))return true;
        if(adjacentPetit && adjacentInterrogant) if(v.contains(toInsert+1)) return true;

        if(adjacentPetit && v.size() == 1) return true;
        if(adjacentGran && v.size() == 1) return true;

    return false;
    }

    private static void processa(String[][] A){
        for(int i=0; i<A.length; ++i){
            for(int j=0; j<A[0].length; ++j) System.out.print(A[i][j]+",");
            System.out.println();
        }
    }

    private static Vector<Integer> numerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        Vector<String> existents = new Vector<>();   //numeros que existeixen a la matrix
        int interrogants = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrix[i][j].equals("?")) {
                    interrogants += 1;
                }
                else if (!matrix[i][j].equals("#") && !matrix[i][j].equals("*")){
                    existents.add(matrix[i][j]);
                }
            }
        }
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + existents.size(); k++){
            if (!existents.contains(Integer.toString(k+1))) total.add(k+1);
        }
        return total;

    }

    public static boolean isInteger(String s) {
        try
        {
            Integer.parseInt(s);
            // s is a valid integer
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

}
