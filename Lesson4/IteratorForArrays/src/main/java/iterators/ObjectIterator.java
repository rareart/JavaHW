package iterators;

import java.util.Iterator;

public class ObjectIterator<T> implements Iterator<T> {

    private final T[] arr;
    private int lastIndex;
    private int removeCounter;

    public ObjectIterator(T[] arr){
        this.arr = arr;
        lastIndex = 0;
        removeCounter = 0;
    }

    @Override
    public boolean hasNext() {
        if (arr.length > lastIndex) {
            if(arr[lastIndex]!=null){
                return true;
            }
        }
        lastIndex = 0; //hasNext reload
        return false;
    }

    @Override
    public T next() {
        return arr[lastIndex++];
    }

    @Override
    public void remove() {
        if(removeCounter<arr.length & lastIndex>0) {
            lastIndex--;
            int nullValue = 0;
            for (int i = lastIndex; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
                nullValue = i+1;
            }
            arr[nullValue] = null;
        }
        removeCounter++;
    }

}

