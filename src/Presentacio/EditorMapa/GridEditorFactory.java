package Presentacio.EditorMapa;

public class GridEditorFactory {
    public GridEditor getGridEditor(String[][] matrix, int columnes, int files, String topologia)
    {
        GridEditor gE = null;
        switch (topologia)
        {
            case ("Quadrats"):
            {
                gE = new GridEditorQuadrats(matrix, columnes, files, topologia);
                break;
            }
            case ("Triangles"):
            {
                gE = new GridEditorTriangles(matrix, columnes, files, topologia);
                break;
            }
            case ("Hexagons"):
            {
                gE = new GridEditorHexagons(matrix, columnes, files, topologia);
                break;
            }
        }
        return gE;
    }
}
