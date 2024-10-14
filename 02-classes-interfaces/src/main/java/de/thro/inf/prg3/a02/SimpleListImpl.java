package de.thro.inf.prg3.a02;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl implements SimpleList, Iterable<Object> {
    private static class Element {
        Object item;
        Element next;

        Element(Object item) {
            this.item = item;
        }
    }

    private Element head = null;
    private int size = 0;

    @Override
    public void add(Object o) {
        Element newElement = new Element(o);
        if (head == null) {
            head = newElement;
        } else {
            Element current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newElement;
        }
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public SimpleList filter(SimpleFilter filter) {
        SimpleListImpl filteredList = new SimpleListImpl();
        for (Object item : this) {
            if (filter.include(item)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    @Override
    public Iterator<Object> iterator() {
        return new SimpleIteratorImpl();
    }

    private class SimpleIteratorImpl implements Iterator<Object> {
        private Element current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            Object item = current.item;
            current = current.next;
            return item;
        }
    }
}

