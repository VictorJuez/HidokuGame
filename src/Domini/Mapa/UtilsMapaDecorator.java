package Domini.Mapa;

import java.util.Vector;

public class UtilsMapaDecorator extends MapaDecorator {

    protected Vector<Vector<Vector<Integer> > > franjes = new Vector<>(); // franja conte un conjunt de camins
    private int last = -1;
    public UtilsMapaDecorator(Mapa decoratedMap) {
        super(decoratedMap);
    }

    public boolean hidatoValido(){
        decoratedMap.solucio = backtrackingResolucio(decoratedMap.getNumerosExistents());
        return decoratedMap.solucio;
    }
    //retorna la posicio en la tablaAD d'on es troba el seguent element
    public int pista(){
        backtrackingResolucio(this.decoratedMap.getNumerosExistents());
        /*System.out.println("casella amb valor: "+decoratedMap.tablaAD.get(last).getValor());
        if (last != -1) {
            Integer valor = Integer.valueOf(decoratedMap.tablaAD.get(last).getValor());
            valor++;
            String val = valor.toString();
            boolean trobat = false;
            for (int i = 0; i < decoratedMap.tablaAD.get(last).getAd().size(); i++) {
                String anterior = decoratedMap.tablaAD.get(decoratedMap.tablaAD.get(last).getAd().get(i)).getValor();
                decoratedMap.tablaAD.get(decoratedMap.tablaAD.get(last).getAd().get(i)).setValorAdyacencia(val);
                decoratedMap.putValorV(valor);
                trobat = backtrackingResolucio(decoratedMap.getNumerosExistents());
                decoratedMap.tablaAD.get(decoratedMap.tablaAD.get(last).getAd().get(i)).setValorAdyacencia(anterior);
                decoratedMap.borrarV(valor);
                if (trobat) return decoratedMap.tablaAD.get(last).getAd().get(i);
            }
        }*/
        if (last != -1) System.out.println(decoratedMap.tablaAD.get(last).getX() +" , "+decoratedMap.tablaAD.get(last).getY());
        else System.out.println(-1);
        return last;
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
        System.out.println("fent camins a gas");
        if(distancia == 0){
            if(posicio == 0){
                franjes.get(franja).add(cami);
            }
            else{
                for(int i = 0; i < decoratedMap.tablaAD.get(indexAD).getAd().size(); i++){
                    Integer aux = decoratedMap.tablaAD.get(indexAD).getAd().get(i);
                    if (decoratedMap.tablaAD.get(aux).getValor().equals(v.get(posicio-1).toString())){
                        franjes.get(franja).add(cami);
                    }
                }
            }
        }
        else{
            /*boolean t = false;
            for(int i = 0; i < decoratedMap.tablaAD.get(indexAD).getAd().size() && !t; i++){
                Integer aux = decoratedMap.tablaAD.get(indexAD).getAd().get(i);
                if (decoratedMap.tablaAD.get(aux).getValor().equals("?") && !decoratedMap.tablaAD.get(aux).visitat) t = true;
            }*/
            for(int i = 0; i < decoratedMap.tablaAD.get(indexAD).getAd().size(); i++){
                Integer aux = decoratedMap.tablaAD.get(indexAD).getAd().get(i);
                System.out.println("adjacents"+decoratedMap.tablaAD.get(indexAD).getAd().size());
                if (!decoratedMap.tablaAD.get(aux).visitat && decoratedMap.tablaAD.get(aux).getValor().equals("?")){
                    System.out.println(distancia);
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
            System.out.println("v: "+v);
            System.out.println("posicio: "+posicio);
            System.out.println("Total: "+total);
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
                    System.out.println("last en fase beta: "+ last);
                }
                b = inner_backtrackingResolucio(v, posicio + 1, total + distancia + 1, franja + 1);
                //if (b && last == -1) last = buscarLast (v, franjes.get(franja).get(i));
                for (int l = 0; l < franjes.get(franja).get(i).size(); l++) {
                    decoratedMap.tablaAD.get(franjes.get(franja).get(i).get(l)).visitat = false;
                    last = -1;
                }
            }
        }

        return b;
    }

    private int buscarLast(Vector<Integer> v, Vector<Integer> f){//aquí tens la franja
        if(f.size() > 1) {
            if (decoratedMap.tablaAD.get(f.get(f.size() - 2)).getValor().equalsIgnoreCase("?")) {
                return f.get(f.size() - 1);
            }
            for (int i = f.size() - 1; i >= 0; i--) {    //el penultim cas es absurd, pases directament al últim.
                if (decoratedMap.tablaAD.get(f.get(i)).getValor().equalsIgnoreCase("?")) {
                    return f.get(i);//retorna la posicio de tablaAD del ultim numero posat.
                }
            }
        }
        return f.get(f.size()-1);
    }
}
