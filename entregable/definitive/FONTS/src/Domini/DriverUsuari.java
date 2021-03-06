////////////////////////////////////////////////////////
////////////// PROGRAMAT PER VÍCTOR JUEZ ///////////////
////////////////////////////////////////////////////////
package Domini;

import Domini.Mapa.Mapa;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class DriverUsuari {
    private static Scanner myScanner;

    public static void main(String[] args){
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) Insertar un nuevo usuario\n"+
                "\t2) Obtener un usuario\n"+
                "\t3) getAllUsers\n"+
                "\t4) insertar Resultado\n"+
                "\tx) Para salir del juego\n";

        System.out.println(introduction);
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();
        boolean active = true;
        while(active) {
            switch (op){
                case "1":
                    insertarUsuario();
                    break;
                case "2":
                    getUsuari();
                    break;
                case "3":
                    getAllUsers();
                    break;
                case "4":
                    insertarResultado();
                    break;
                case "x":
                    System.out.println("exiting game...");
                    //deleteFiles();
                    active = false;
                    break;
            }

            if (active) {
                System.out.println("---------------------------------------\n\n"
                        +introduction);
                op = myScanner.next();
            }
        }
    }

    private static void insertarResultado() {
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        System.out.println("Inserte la puntuacion");
        int puntuacion = myScanner.nextInt();
        Usuari usuari = ControladorUsuari.getUsuari(id);
        boolean record = ControladorUsuari.insertarResultat(usuari, puntuacion);
        if(record) System.out.println("New record!");
    }

    private static void getAllUsers() {
        HashMap<String, Usuari> au = ControladorUsuari.getAllUsers();
        au.forEach((k,v) -> {
            printUsuari(v);
        });
    }

    private static void insertarUsuario() {
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        System.out.println("Escribe la contraseña del usuario");
        String password = myScanner.next();

        Usuari usuari = ControladorUsuari.insertarUsuari(id, password);
        if(usuari == null) System.out.println("l'usuari ja existeix");
        else printUsuari(usuari);
    }

    private static void printUsuari(Usuari usuari) {
        System.out.println("usuari id: "+usuari.getID());
        System.out.println("usuari password: "+usuari.getPassword());
        System.out.println("puntuacio: " + usuari.getPuntuacio());
        System.out.println("record: " + usuari.getRecord());
        System.out.println("");
        System.out.println("partidas del usuario: ");
        for(String partidaID : usuari.getPartidasID()) System.out.print(partidaID+",");
        System.out.println();
    }

    private static void getUsuari(){
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        printUsuari(ControladorUsuari.getUsuari(id));
    }

    public static void deleteFiles() {
        String[] pathNames = {"usuaris", "mapas","partidas"};
        for(String pathName : pathNames) {
            for (File file : new File("data/" +pathName+"/").listFiles()) {
                if (!file.getName().equals(".gitignore")) {
                    //do nothing
                    file.delete();
                }
            }
        }
    }

}
