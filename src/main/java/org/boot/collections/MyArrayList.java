package org.boot.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyArrayList<E> implements CustomList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size = 0;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    @Override
    public void add(E element) {
        if (elements.length == size) {
            increaseCapacity();
        }
        elements[size++] = element;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (elements.length == size) {
            increaseCapacity();
        }
        System.arraycopy(elements, index, elements,
                index + 1, size - index);
        size++;
        elements[index] = element;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        int newMoved = size - index - 1;
        if (newMoved > 0) {
            System.arraycopy(elements, index + 1,
                    elements, index, newMoved);
        }
        elements[--size] = null;
    }

    @Override
    public E getFirst() {
        return (E) elements[0];
    }

    @Override
    public E getLast() {
        return (E) elements[size - 1];
    }

    @Override
    public boolean remove(E o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        elements[index] = element;
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean contains(E o) {
        return indexOf(o) != -1;
    }

    @Override
    public int indexOf(E o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public String toString() {
        Object[] actual = Arrays.copyOf(elements, size);
        return "MyArrayList { elements=" + Arrays.toString(actual) + '}';
    }

    private void increaseCapacity() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        MyArrayList<?> myArrayList = (MyArrayList<?>) o;

        return Arrays.equals(elements, myArrayList.elements) &&
                size == myArrayList.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(elements), size);
    }

    private class MyIterator implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) elements[cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove not implemented");
        }
    }
}
