package Domini.Mapa;

import java.util.Vector;

public class UtilsMapaDecorator extends MapaDecorator {

    protected Vector<Vector<Vector<Integer> > > franjes = new Vector<>(); // franja conte un conjunt de camins
    private int last = -1;

    public UtilsMapaDecorator(Mapa decoratedMap) {
        super(decoratedMap);
    }

    /**
     * Pre: en el hidato tienen que estar puestos el primer y ultimo numero del camino, sino no lo resolvera
     * Calcula si el Hidato de un mapa tiene solucion
     * @return Devuelve true si tiene solucion, en caso contrario devuelve false
     */
    public boolean hidatoValido(){
        decoratedMap.solucio = backtrackingResolucio(decoratedMap.getNumerosExistents());
        return decoratedMap.solucio;
    }

    /**
     * Si el mapa esta en un estado de posible solucion devuelve que casilla es la siguiente a poner, en caso contrario devuelve una casilla -1, -1
     * @return  Devuelve dos enteros que indican que fila y columna va el siguiente numero
     */
    public Integer[] pista(){
        Integer[] p = new Integer[2];
        backtrackingResolucio(this.decoratedMap.getNumerosExistents());
        if (last != -1){
            p[0] = decoratedMap.tablaAD.get(last).getX();
            p[1] = decoratedMap.tablaAD.get(last).getY();
        }
        else{
            p[0] = -1;
            p[1] = -1;
        }
        return p;
    }

    /**
     * Busca en que posicion de la tabla de adyacencias se encuentra un valor, en caso de no existir devuelve -1
     * @param valor
     * @return  Devuelve un entero que indica en que posicio de la tabla de adyacencias se encuentra la casilla con el valor que pasamos como parametro
     */
    protected Integer busca(String valor){       //et retorna la posicio don es troba el valor a la taula d'adjacencies
        for (int i = 0; i < decoratedMap.tablaAD.size(); i++){
            if(decoratedMap.tablaAD.get(i).getValor().equals(valor)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Funcion que calcula si el mapa en cuestion tiene solucion, si la tiene devuelve true, sino false
     * @param v
     * @return Devuelve un booleano que indica si tiene solucion
     */
    private boolean backtrackingResolucio(Vector v) {
        return inner_backtrackingResolucio(v, 0, 0, 0);
    }

    /**
     * Calcula la distancia que hay entre dos numeros consecutivos que ya estan en el hidato
     * @param posicio
     * @param v
     * @return Devuelve un entero que indica la distancia entre dos valores consecutivos
     */
    private Integer calculDistancia(Integer posicio, Vector<Integer> v){
        if (posicio == 0) return 0;
        else return v.get(posicio) - v.get(posicio - 1) - 1;
    }

    /**
     * Funcion que calculo todos los posibles caminos entre los numeros que se encuentran en el mapa y los guarda en el vector franjes
     * @param posicio
     * @param distancia
     * @param v
     * @param cami
     * @param indexAD
     * @param franja
     */

    private void calculCamins(Integer posicio, Integer distancia, Vector v, Vector cami, Integer indexAD, Integer franja) {//posicio es la posicio del vector dexistens
        if(distancia == 0){
            if(posicio == 0){
                Vector<Integer> c1 = new Vector<>(cami);
                franjes.get(franja).add(c1);
            }
            else{
                for(int i = 0; i < decoratedMap.tablaAD.get(indexAD).getAd().size(); i++){
                    Integer aux = decoratedMap.tablaAD.get(indexAD).getAd().get(i);
                    if (decoratedMap.tablaAD.get(aux).getValor().equals(v.get(posicio-1).toString())){
                        Vector<Integer> c1 = new Vector<>(cami);
                        franjes.get(franja).add(c1);
                    }
                }
            }
        }
        else{
            for(int i = 0; i < decoratedMap.tablaAD.get(indexAD).getAd().size(); i++){
                Integer aux = decoratedMap.tablaAD.get(indexAD).getAd().get(i);
                if (!decoratedMap.tablaAD.get(aux).visitat && decoratedMap.tablaAD.get(aux).getValor().equals("?")){
                    cami.add(aux);
                    decoratedMap.tablaAD.get(aux).visitat = true;
                    calculCamins(posicio,distancia -1, v, cami, aux, franja);
                    decoratedMap.tablaAD.get(aux).visitat = false;
                    cami.remove(aux);
                }
            }
        }

    }

    /**
     * Funcion interna al backtracking que ayuda a saber si tiene solucion o no
     * @param v
     * @param posicio
     * @param total
     * @param franja
     * @return  Devuelve true en caso de haber solucion, sino devuelve false
     */
    private boolean inner_backtrackingResolucio( Vector v, Integer posicio, Integer total, Integer franja){
        boolean b = false;
        if (total == decoratedMap.getInterrogants() + decoratedMap.getNumeros()) return true;
        else{
            Vector<Integer> cami = new Vector<>();
            String valor;
            int indexAD;
            valor = v.get(posicio).toString();
            indexAD = busca(valor);
            Integer distancia = calculDistancia(posicio, v);
            franjes.add(franja, new Vector<>());
            decoratedMap.tablaAD.get(indexAD).visitat = true;
            cami.add(indexAD);//aqui afageixes caselles amb numeros
            calculCamins(posicio, distancia, v, cami, indexAD, franja);
            decoratedMap.tablaAD.get(indexAD).visitat = false;
            for (int i = 0; i < franjes.get(franja).size() && !b; i++) {
                int k = 0;
                for (; k < franjes.get(franja).get(i).size(); k++) {
                    decoratedMap.tablaAD.get(franjes.get(franja).get(i).get(k)).visitat = true;
                    if (last == -1 && decoratedMap.tablaAD.get(franjes.get(franja).get(i).get(k)).getValor().equals("?")) last = franjes.get(franja).get(i).get(k);
                }
                b = inner_backtrackingResolucio(v, posicio + 1, total + distancia + 1, franja + 1);
                for (int l = 0; l < franjes.get(franja).get(i).size(); l++) {
                    decoratedMap.tablaAD.get(franjes.get(franja).get(i).get(l)).visitat = false;
                    if (!b)last = -1;
                }
            }
        }

        return b;
    }

    /**
     * Devuelve si un hidato tiene el primer y ultimo numero puestos
     * @return devyelve true si estan los dos numeros, en caso contrario devulve false
     */
    public boolean principioFin(){
        Vector<adyacencias> v = decoratedMap.getTablaAD();
        boolean primer = false;
        boolean ultim = false;
        int total = v.size();
        String fi = String.valueOf(total);
        for (int i = 0; i < total; i++){
            if (v.get(i).getValor().equals("1"))primer = true;
            else if(v.get(i).getValor().equals(fi)) ultim = true;
        }
        return primer & ultim;
    }
}
