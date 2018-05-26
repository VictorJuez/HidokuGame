package Domini;

import java.util.Scanner;

public class DriverUsuari {
    private static Scanner myScanner;
    private static ControladorUsuari controladorUsuari = new ControladorUsuari();
    private static ControladorMapa controladorMapa = new ControladorMapa();

    public static void main(String[] args){
        System.out.println("Hidato Game");
        String introduction = "Introduce qué operación desea ejecutar:\n"+
                "\t1) Insertar un nuevo usuario\n"+
                "\t2) Obtener un usuario\n"+
                "\t3) Add Map to User\n"+
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
                    addMaptoUser();
            }

            if (active) {
                System.out.println("---------------------------------------\n\n"
                        +introduction);
                op = myScanner.next();
            }
        }
    }

    private static void addMaptoUser() {
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        Usuari usuari = controladorUsuari.getUsuari(id);
        controladorUsuari.addMapatoUser(usuari.getID(), controladorMapa.generarHidato().getID());
        printUsuari(usuari);
    }

    private static void insertarUsuario() {
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        System.out.println("Escribe la contraseña del usuario");
        String password = myScanner.next();

        Usuari usuari = controladorUsuari.insertarUsuari(id, password);

        printUsuari(usuari);
    }

    private static void printUsuari(Usuari usuari) {
        System.out.println("usuari id: "+usuari.getID());
        System.out.println("usuari password: "+usuari.getPassword());
        for(String partidaID : usuari.getPartidasID()) System.out.print(partidaID+",");
        System.out.println();
        for(String mapaID : usuari.getMapasID()) System.out.print(mapaID+",");
        System.out.println();
    }

    private static void getUsuari(){
        System.out.println("Escribe el id del usuario");
        String id = myScanner.next();
        printUsuari(controladorUsuari.getUsuari(id));
    }

}
