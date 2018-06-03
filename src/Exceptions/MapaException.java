package Exceptions;

public class MapaException extends Exception {
    public MapaException() { super(); }
    public MapaException(String message) { super(message); }
    public MapaException(String message, Throwable cause) { super(message, cause); }
    public MapaException(Throwable cause) { super(cause); }
}
