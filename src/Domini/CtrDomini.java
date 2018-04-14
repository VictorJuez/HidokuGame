package Domini;

public class CtrDomini {

    public CtrDomini() {
    }

    public void helloWorld() {
        System.out.println("Hello World!");
    }

    public void insertarHidato(int filas, int columnas, String[][] tab) {

        Tablero t = new Tablero(filas,columnas,tab);
        t.print();
    }

    public void generarHidato(){
        //Mahias aqui es donde puedes poner tu funcion.
    }

}
