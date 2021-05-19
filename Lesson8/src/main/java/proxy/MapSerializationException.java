package proxy;

public class MapSerializationException extends Exception {
    public MapSerializationException() {
    }

    public MapSerializationException(String message) {
        super(message);
    }

    public MapSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapSerializationException(Throwable cause) {
        super(cause);
    }
}
