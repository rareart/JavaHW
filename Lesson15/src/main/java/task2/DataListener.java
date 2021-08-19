package task2;

public interface DataListener<T> {
    void onEvent(T result) throws DataListenerException;
    default <D> void additionalData(D data) { }
}
