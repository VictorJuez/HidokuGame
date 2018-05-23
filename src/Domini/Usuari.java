package Domini;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Usuari {
    protected String ID;
    protected static Map <String, String> instances = Collections.emptyMap();  //estructura de datos donde guardaremos todos los usuarios a medida que se van creando y borrando
    protected static String actual = null;    //usuario con la sesion abierta, no se si es pot fer aixo
    protected String password;  //password del usuario
    protected Vector<String> partidas = new Vector<String>();   //partidas del usuario
    protected Vector<String> mapas = new Vector<String>();      //mapas que ha creado el usuario

    public Usuari(String ID){
        this.ID = ID;
    }   //creadora

    public boolean crearUsuari(String ID, String password){     //retorna true si s'ha creat l'usari amb exit, en cas contrari retorna false
        if (instances.containsKey(ID)) return false;
        else{
            instances.put(ID,password);
            this.ID = ID;
            this.password = password;
        }
        return true;
    }

    public void borrarUsuari(){
        if (actual == this.ID) actual = null;   //si borrem l'usuari actual, aleshores el posem a null
        instances.remove(ID);
        this.partidas = new Vector<String>();
        this.mapas = new Vector<String>();      //reinicialitzem les estructures
        this.ID = null;
        this.password = null;
    }

    public String getID(){
        return this.ID;
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        Usuari u = (Usuari) obj;
        return this.ID.equals(u.getID());
    }


}
