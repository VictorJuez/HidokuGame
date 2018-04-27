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
        backtrackingResolucio(0,0, A, v);
    }

    private static void backtrackingResolucio(int x, int y, String[][] A, Vector v){
        System.out.println("Now iterating over: "+x+", "+y);
        System.out.println(v+"\n");
        if(y >= A[0].length) {
            y=0;
            if(x+1 >= A.length) processa(A);
            else ++x;
        }
        if(v.size() == 0) {
            System.out.println("finiished");
            processa(A);
        }
        else{
            if(A[x][y].equals("?")){
                System.out.println("Interrogant al ["+x+"],["+y+"]");
                for(int i =0; i<v.size(); ++i){
                    int aux = (int) v.get(i);
                    for(int k=1; k<=4; ++k){
                        int xx = x;
                        int yy = y;
                        switch (k){
                            case 1: //checking left
                                xx=x;
                                yy=y-1;
                                break;
                            case 2: //checking up
                                xx=x-1;
                                yy=y;
                                break;
                            case 3: //checking right
                                xx=x;
                                yy=y+1;
                                break;
                            case 4: //checking down
                                xx=x+1;
                                yy=y;
                                break;
                        }

                        if(posicioCorrecte(xx,yy,A, (Integer) v.get(i))){
                            System.out.println("Posicio correcte! "+xx+", "+yy+"| Posem el "+aux);
                            //checking left
                            A[x][y] = String.valueOf(aux);
                            v.remove(i);
                            backtrackingResolucio(x,++y,A,v);
                            v.add(i,aux);
                            A[x][y] = "?";
                        }
                    }
                }
            }
            else {
                backtrackingResolucio(x, ++y, A, v);
            }
        }
    }

    private static boolean posicioCorrecte(int x, int y, String[][] A, int toInsert){
        if(x<0 || y<0 || x>=A.length || y>=A[0].length) return false;
        try {
            int tableValue = Integer.parseInt(A[x][y]);
            if (tableValue == toInsert-1 || tableValue == toInsert+1) {
                System.out.println("tableValue: "+ tableValue + ", toInsert"+ toInsert);
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    private static void processa(String[][] A){
        for(int i=0; i<A.length; ++i){
            for(int j=0; j<A[0].length; ++j) System.out.print(A[i][j]);
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

}
