package Domini;

import java.util.ArrayList;
import java.util.HashMap;

public class Ranking {
    //Singleton pattern
    private static Ranking instance = new Ranking();
    private HashMap<String, Integer> status = new HashMap<>();
    private final ArrayList<String> users;
    private ControladorUsuari ctUsuari = new ControladorUsuari();
    private Ranking(){
        users = ctUsuari.getAllUsers();
        initializeStatus();
    }

    private void initializeStatus() {
        for(int i=0; i<users.size(); ++i) status.put(users.get(i),0);
    }

    public static Ranking getInstance(){
        return instance;
    }

    public void insertResult(String ID, int result){
        int actualValue = status.get(ID);
        status.put(ID, actualValue+result);
    }

    public int getResult(String ID){
        return status.get(ID);
    }

    public HashMap<String, Integer> getStatus() {
        return status;
    }
}
