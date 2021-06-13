package threadPool;

import java.util.ArrayDeque;

public class DequeBasedSynchronizedQueue<T> {
    private final ArrayDeque<T> dequeStore;

    public DequeBasedSynchronizedQueue() {
        this.dequeStore = new ArrayDeque<>();
    }

    public synchronized T poll(){
        return this.dequeStore.poll();
    }

    public synchronized void add(T element){
        this.dequeStore.add(element);
    }

    public synchronized boolean notEmpty(){
        return !this.dequeStore.isEmpty();
    }

    public synchronized int size(){
        return this.dequeStore.size();
    }
}
