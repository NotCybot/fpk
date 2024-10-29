package de.thro.inf.prg3.a04.collections;


import java.util.Iterator;

public class SimpleListImpl<T> implements SimpleList<T> {

    private ListElement<T> head;

    private int size;

    public SimpleListImpl() {
        head = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {

        ListElement<T> current = head;
        int n = 0;
        while (current != null && n != index) {
            current = current.getNext();
            n++;
        }
        if (current != null) {
            return current.item;
        }
        return null;
    }

    @Override
    public void set(int index, T item) {
        ListElement<T> current = head;
        int n = 0;

        if (index > size() - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        } else {
            while (current != null && n != index) {
                current = current.getNext();
                n++;
            }
            if (current != null && item != null) {
                current.setItem(item);
            }
        }
    }

    @Override
    public void add(T item) {
        if (head == null) {
            head = new ListElement<>(item);
        } else {
            ListElement<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new ListElement<>(item));
        }
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator();
    }

    private class SimpleIterator implements Iterator<T> {

        private ListElement<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T tmp = current.getItem();
            current = current.getNext();
            return tmp;
        }
    }

    private static class ListElement<T> {

        T item;

        private ListElement<T> next;

        public ListElement(T item) {
            this.item = item;
            this.next = null;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public ListElement<T> getNext() {
            return next;
        }

        public void setNext(ListElement<T> next) {
            this.next = next;
        }
    }

}

