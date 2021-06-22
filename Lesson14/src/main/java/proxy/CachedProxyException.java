package proxy;

public class CachedProxyException extends Exception {
    public CachedProxyException() {
    }

    public CachedProxyException(String message) {
        super(message);
    }

    public CachedProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CachedProxyException(Throwable cause) {
        super(cause);
    }
}
