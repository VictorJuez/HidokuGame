package Domini.Mapa;

public abstract class MapaDecorator {
    protected Mapa decoratedMap;

    public MapaDecorator (Mapa decoratedMap){
        this.decoratedMap = decoratedMap;
    }
}
