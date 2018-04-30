package Domini;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class Mapa {
    protected String ID;
    protected static List<String> instances = new ArrayList();
    protected boolean adyacenciaAngulos;
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected boolean teSolucio;
    protected String[][] matrix;

    public Mapa(){}

    public String getID() {
        return ID;
    }

    public static List getInstances() {
        return instances;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    protected Vector<Integer> numerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        Vector<String> existents = new Vector<>();   //numeros que existeixen a la matrix
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
        numeros = existents.size();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + existents.size(); k++){
            if (!existents.contains(Integer.toString(k+1))) total.add(k+1);
        }
        return total;

    }

    protected boolean isInteger(String s) {
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

    protected void processa(String[][] A){
        for(int i=0; i<A.length; ++i){
            for(int j=0; j<A[0].length; ++j) System.out.print(A[i][j]+",");
            System.out.println();
        }
        this.teSolucio = true;
    }

    protected void hidatoValido(){
        String[][] A = matrix;
        Vector<Integer> v;
        v = numerosRestants();
        backtrackingResolucio(A, v);
    }

    protected boolean backtrackingResolucio(String[][] A, Vector v){
        //if(k>=filas*columnas) return;
        System.out.println(v);
        if(v.size() == 0) {
            processa(A);
            return true;
        }
        else{
            boolean b = false;
            for(int i=0; i<A.length && !b; ++i) {
                for(int j=0; j<A[0].length; ++j) {
                    if (A[i][j].equals("?")) {
                        int aux = (int) v.get(0);
                        if (posicioCorrecte(i, j, A, aux, v)) {
                            A[i][j] = String.valueOf(aux);
                            //processa(A);
                            v.remove(0);
                            b = backtrackingResolucio(A, v);
                            v.add(0, aux);
                            A[i][j] = "?";
                        }
                    }
                }
            }
        }
        return true;
    }

    abstract protected boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);
}
