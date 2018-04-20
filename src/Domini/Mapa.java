package Domini;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    protected String ID;
    protected static List<String> instances = new ArrayList();

    public Mapa(){}

    public String getID() {
        return ID;
    }

    public static List getInstances() {
        return instances;
    }
}
