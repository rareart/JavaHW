package com.company.lesson2;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;

public class ReverseIterator implements Iterator<Object> {

    private final ArrayDeque<Object> internalArrayDeque;

    public ReverseIterator(List<?> list){
        internalArrayDeque = new ArrayDeque<>();
        for (Object o : list){
            internalArrayDeque.push(o);
        }
    }

    @Override
    public boolean hasNext() {
        return internalArrayDeque.peek()!=null;
    }

    @Override
    public Object next() {
        return internalArrayDeque.pop();
    }

    @Override
    public void remove() {
        internalArrayDeque.remove();
    }


}
