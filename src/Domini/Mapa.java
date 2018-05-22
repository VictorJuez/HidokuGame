package Domini;

import javafx.util.Pair;
import jdk.internal.cmm.SystemResourcePressureImpl;

import java.util.Collections;
import java.util.UUID;
import java.util.Vector;


public abstract class Mapa {
    protected String ID;
    protected int filas;
    protected int columnas;
    protected int interrogants;
    protected int numeros;
    protected String tipo;
    protected String angulos;
    protected Vector<Integer> numerosExistents;
    protected Vector<Integer> numerosRestants;
    protected String[][] matrix;
    protected String[][] solutionMatrix;
    protected boolean solucio;

    protected class adyacencias{
        private String valor;
        boolean visitat;
        private Integer x;
        private Integer y;
        private Integer z;
        Vector<Integer> ad;

        public adyacencias(int y, int x, String valor){
            this.y = y;
            this.x = x;
            this.z = x + y*columnas;
            this.valor = valor;
            this.visitat = false;
            this.ad = new Vector<>();
        }

        public Integer getZ() {
            return z;
        }

        public Integer getX() {
            return x;
        }
        public Integer getY() {
            return y;
        }

        public String getValor() {
            return valor;
        }
    };

    protected Vector <adyacencias> tablaAD = new Vector<>();
    protected Vector<Vector<Vector<Integer> > > franjes = new Vector<>(); // franja conte un conjunt de camins

    protected abstract Vector<adyacencias> calculoAdyacencias();

    public Mapa(String[][] matrix) {
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;

        inicialitzaTabla();
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(this.matrix);
        numeros = numerosExistents.size();
    }
    public Mapa(){
        this.ID = UUID.randomUUID().toString();
        this.solucio = false;
    }
    public Mapa(String ID, String[][] matrix){
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        this.ID = ID;
        this.solucio = false;

        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(this.matrix);
        numeros = numerosExistents.size();
    }

    public Vector<adyacencias> getTablaAD() {
        if(tablaAD.size() == 0)tablaAD = calculoAdyacencias();
        return tablaAD;
    }
    public String getID() {
        return ID;
    }
    public int getFilas() {
        return filas;
    }
    public int getColumnas() {
        return columnas;
    }
    public String getTipo() {
        return tipo;
    }
    public String getAngulos() {
        return angulos;
    }
    public boolean isSolucio() {
        return solucio;
    }
    public String[][] getMatrix() {
        return matrix;
    }
    public String[][] getSolutionMatrix() {
        return solutionMatrix;
    }
    public void insertarNumero(int numero, int x, int y){
        solutionMatrix[x][y] = String.valueOf(numero);
    }
    public void borrarNumero(int x, int y){
        solutionMatrix[x][y] = "?";
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        this.solutionMatrix = copyMatrix(matrix);
        this.filas = matrix.length;
        this.columnas = matrix[0].length;
        numerosExistents = getNumerosExistents();
        numerosRestants = getNumerosRestants();
        interrogants = getInterrogants(matrix);
        numeros = numerosExistents.size();
        calculoAdyacencias();
    }
    public Vector<Integer> getNumerosExistents(){
        Vector<Integer> existents = new Vector<>();   //numeros que existeixen a la matrix
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!matrix[i][j].equals("#") && !matrix[i][j].equals("*") && !matrix[i][j].equals("-2") && !matrix[i][j].equals("?")){
                    existents.add(Integer.valueOf(matrix[i][j]));
                }
            }
        }
        Collections.sort(existents);
        return existents;
    }
    public int getInterrogants(String[][] matrix){
        int interrogants = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrix[i][j].equals("?")) {
                    interrogants += 1;
                }
            }
        }
        return interrogants;
    }
    public boolean hidatoValido(){
        Vector<Integer> v;
        v = getNumerosExistents();
        this.solucio = backtrackingResolucio(solutionMatrix, v);
        return this.solucio;
    }

    private String[][] copyMatrix(String[][] matrix){
        solutionMatrix = new String[filas][columnas];
        for(int i=0; i<filas; ++i){
            for(int j=0; j<columnas; ++j) solutionMatrix[i][j] = matrix[i][j];
        }
        return solutionMatrix;
    }

    public abstract Integer[][] pathFinder(int casillas_validas, int numero_fil, int numero_col);
    //public abstract boolean posicioCorrecte(int x, int y, String[][] A, int toInsert, Vector<Integer> v);
    public abstract boolean matriuCorrecte();

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
    protected boolean casillaValida(int i, int j, int num_filas, int num_col, Integer[][] casillas_visitadas) {
        if (i < (num_filas - 1) && j < (num_col - 1) && i > 0 && j > 0) {
            if (casillas_visitadas[i][j] == -1) return true;
        }
        return false;
    }
    protected Vector<Integer> getNumerosRestants(){   //aixo es podria guardar tot com si fos un atribut
        if(numerosExistents == null) numerosExistents = getNumerosExistents();
        Vector<Integer> total = new Vector<>();
        for(int k = 0; k < interrogants + numerosExistents.size(); k++){
            if (!numerosExistents.contains(k+1)) total.add(k+1);
        }
        return total;









    }
    protected Integer busca(String valor){       //et retorna la posicio don es troba el valor a la taula d'adjacencies
        for (int i = 0; i < tablaAD.size(); i++){
            if(tablaAD.get(i).valor.equals(valor)){
                return i;
            }
        }
        return -1;
    }


    protected boolean backtrackingResolucio(String[][] A, Vector v) {   //si v esta buid no funciona!!!!!!!!
        if(tablaAD.size() == 0)tablaAD = calculoAdyacencias();
        return inner_backtrackingResolucio(v, 0, 0, 0);
    }


    protected Integer calculDistancia(Integer posicio, Vector<Integer> v){
        //System.out.println(posicio +" " + v);//en principi actual nomes et poden donar valors de v, el numero 1 o lultim numero (not sure daixo ultim)
        if(!v.isEmpty()) {
            if (posicio == 0) return v.get(posicio) -1;
            else if (posicio == v.size()) return numeros + interrogants - v.get(posicio - 1);
            else return v.get(posicio) - v.get(posicio - 1) - 1;
        }
        return numeros + interrogants; //cas en que tot son interrogants i v esta buit
    }



    protected void calculCamins(Integer posicio, Integer distancia, Vector v, Vector cami, Integer indexAD, Integer franja) {//posicio es la posicio del vector dexistens
        //System.out.println("estic fent el cami: "+cami);
        //System.out.println("parteixo d'aquest valor: "+v);
        if(distancia == 0){
            if(posicio == 0 || posicio == v.size()){
                franjes.get(franja).add(cami);
                //System.out.println("es un cami: "+cami);
            }
            else{
                for(int i = 0; i < tablaAD.get(indexAD).ad.size(); i++){
                    Integer aux = tablaAD.get(indexAD).ad.get(i);
                    if (tablaAD.get(aux).getValor().equals(v.get(posicio-1).toString())){
                        //System.out.println("eiii he entrat a la franja: "+ franja);
                        franjes.get(franja).add(cami);
                        //System.out.println("es un cami: "+cami);
                    }
                }
            }
        }
        else{
            for(int i = 0; i < tablaAD.get(indexAD).ad.size(); i++){
                Integer aux = tablaAD.get(indexAD).ad.get(i);
                if (!tablaAD.get(aux).visitat && tablaAD.get(aux).getValor().equals("?")){
                    cami.add(aux);
                    tablaAD.get(aux).visitat = true;
                    calculCamins(posicio,distancia -1, v, cami, aux, franja);
                    tablaAD.get(aux).visitat = false;
                    cami.remove(aux);
                }
            }
        }

    }


    protected boolean inner_backtrackingResolucio( Vector v, Integer posicio, Integer total, Integer franja){
       // System.out.println("total: "+ total + " valor de v[posicio]: "+ v.get(posicio));
        System.out.println(total);
        boolean b = false;
        if (total == interrogants + numeros) return true;
        else{
            Vector<Integer> cami = new Vector<>();
            Integer x = -1;
            String valor = x.toString();
            int indexAD = -1;
            boolean last = false;
            if (posicio < v.size()) {
                valor = v.get(posicio).toString();
                indexAD = busca(valor);
            }
            else if (posicio == v.size()){
                valor = v.get(posicio-1).toString();
                indexAD = busca(valor);
                last = true;
            }
            Integer distancia = calculDistancia(posicio, v);

            franjes.add(franja, new Vector<>());

            if (indexAD != -1)tablaAD.get(indexAD).visitat = true;
            cami.add(indexAD);//aqui afageixes caselles amb numeros
            calculCamins(posicio, distancia, v, cami, indexAD, franja);
            if (indexAD != -1)tablaAD.get(indexAD).visitat = false;

           // System.out.println("aquests son el numero de camins: "+ franjes.get(franja).size());

            for (int i = 0; i < franjes.get(franja).size() && !b; i++) {
                for (int k = 0; k < franjes.get(franja).get(i).size(); k++) {
                    tablaAD.get(franjes.get(franja).get(i).get(k)).visitat = true;
                }
                int suma = 1;
                if (last) suma = 0;
                b = inner_backtrackingResolucio(v, posicio + 1, total + distancia + suma, franja + 1);
                for (int k = 0; k < franjes.get(franja).get(i).size(); k++) {
                    tablaAD.get(franjes.get(franja).get(i).get(k)).visitat = false;
                }
            }

        }
        return b;

    }

    void inicialitzaTabla(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                if(!matrix[i][j].equals("#") && !matrix[i][j].equals("*")){
                    adyacencias a = new adyacencias(i,j, matrix[i][j]);
                    tablaAD.add(a);
                }
            }
        }
    }
}
