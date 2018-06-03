package Exceptions;

public class UsuariException extends Exception {
    public UsuariException() { super(); }
    public UsuariException(String message) { super(message); }
    public UsuariException(String message, Throwable cause) { super(message, cause); }
    public UsuariException(Throwable cause) { super(cause); }
}
