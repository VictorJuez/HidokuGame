package Dades;

import Domini.Mapa;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import Domini.MapaFactory;
import Domini.Partida;

public class PartidaDAO {

    public static void savePartida (Partida p) throws IOException {

        Properties properties = new Properties();
        properties.setProperty("ID", p.getID());
        properties.setProperty("topologia", p.getTipoMapa());
        properties.setProperty("adyacencia", p.getAngulosMapa());
        properties.setProperty("filas", String.valueOf(p.getFilasMapa()));
        properties.setProperty("columnas", String.valueOf(p.getColumnasMapa()));
        String matrixString = null;
        String[][] matrix = p.getMatrixMapa();
        for(int i=0; i<p.getFilasMapa(); ++i){
            for(int j=0; j<p.getColumnasMapa(); ++j) {
                if(i==0 && j==0) matrixString = matrix[i][j]+",";
                else matrixString+=matrix[i][j]+",";
            }
        }
        matrixString = matrixString.substring(0,matrixString.length()-1);
        properties.setProperty("matrix", matrixString);

        //esto para saber que numeros estaban puestos ya en el inicio
        String numerosInicio = p.getNumerosInicio();
        properties.setProperty("numeros inicio", numerosInicio);
        //esto los que hemos añadido nosotros
        String numerosInsertados = p.getNumerosInsertados();
        properties.setProperty("numeros insertados", numerosInsertados);

        properties.setProperty("reloj", String.valueOf(p.getReloj()));

        File file2 = new File("data/partidas/"+p.getID()+".properties");
        FileOutputStream fileOut = new FileOutputStream(file2);
        properties.store(fileOut, "Partida: |" +p.getID()+"| properties");
        fileOut.close();
    }

}
