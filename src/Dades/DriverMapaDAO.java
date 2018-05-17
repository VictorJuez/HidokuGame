package Dades;

import java.io.IOException;
import java.util.Scanner;

public class DriverMapaDAO {
    private static Scanner myScanner;
    private static MapaDAO mdao = new MapaDAO();
    public static void main(String[] args) throws IOException {
        String op = "";
        myScanner = new Scanner(System.in);
        op = myScanner.next();
        mdao.loadMapa(op);
    }
}
