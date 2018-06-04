package Domini.Mapa;

import java.util.Vector;

public class UtilsMapaDecorator extends MapaDecorator {

    protected Vector<Vector<Vector<Integer> > > franjes = new Vector<>(); // franja conte un conjunt de camins
    private int last = -1;
    public UtilsMapaDecorator(Mapa decoratedMap) {
        super(decoratedMap);
    }

    public boolean hidatoValido(){
        System.out.println(decoratedMap.getNumerosExistents().size());
        decoratedMap.solucio = backtrackingResolucio(decoratedMap.getNumerosExistents());
        return decoratedMap.solucio;
    }
    //retorna la posicio en la tablaAD d'on es troba el seguent element
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

    protected Integer busca(String valor){       //et retorna la posicio don es troba el valor a la taula d'adjacencies
        for (int i = 0; i < decoratedMap.tablaAD.size(); i++){
            if(decoratedMap.tablaAD.get(i).getValor().equals(valor)){
                return i;
            }
        }
        return -1;
    }

    private boolean backtrackingResolucio(Vector v) {   //si v esta buid no funciona!!!!!!!!
        return inner_backtrackingResolucio(v, 0, 0, 0);
    }

    private Integer calculDistancia(Integer posicio, Vector<Integer> v){
        if (posicio == 0) return 0;
        else return v.get(posicio) - v.get(posicio - 1) - 1;
    }



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
}
