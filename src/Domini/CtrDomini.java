package Domini;

public class CtrDomini {

    public CtrDomini() {
    }

    public void helloWorld() {
        System.out.println("Hello World!");
    }

    public Tablero insertarHidato(int filas, int columnas, String[][] tab) {

        Tablero t = new Tablero(filas,columnas,tab);
        return t;
    }



    public void generarHidato(){
        //Mahias aqui es donde puedes poner tu funcion.
    }

}
