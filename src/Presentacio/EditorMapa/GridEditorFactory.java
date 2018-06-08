////////////////////////////////////////////////////////
////////PROGRAMAT PER MATHIAS BERTORELLI ARGIBAY////////
////////////////////////////////////////////////////////
package Presentacio.EditorMapa;

public class GridEditorFactory {
    /**
     * Crea una instancia de GridEditor segons la tipologia escollida
     * @param nomMapa
     * @param matrix
     * @param columnes
     * @param files
     * @param topologia
     * @return GridEditor Quadrats, Triangles o Hexagons en funció del que hem escollit
     */
    public GridEditor getGridEditor(String nomMapa, String[][] matrix, int columnes, int files, String topologia)
    {
        GridEditor gE = null;
        switch (topologia)
        {
            case ("Quadrats"):
            {
                gE = new GridEditorQuadrats(nomMapa, matrix, columnes, files, topologia);
                break;
            }
            case ("Triangles"):
            {
                gE = new GridEditorTriangles(nomMapa, matrix, columnes, files, topologia);
                break;
            }
            case ("Hexagons"):
            {
                gE = new GridEditorHexagons(nomMapa, matrix, columnes, files, topologia);
                break;
            }
        }
        return gE;
    }
}
