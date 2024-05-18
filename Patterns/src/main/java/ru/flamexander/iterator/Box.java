package ru.flamexander.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


public class Box {
    private final List<String> list1;
    private final List<String> list2;
    private final List<String> list3;
    private final List<String> list4;

    private class MyIterator implements Iterator<String> {
        private final Iterator<String> it1 = list1.iterator();
        private final Iterator<String> it2 = list2.iterator();
        private final Iterator<String> it3 = list3.iterator();
        private final Iterator<String> it4 = list4.iterator();

        @Override
        public boolean hasNext() {
            return it1.hasNext() || it2.hasNext() || it3.hasNext() || it4.hasNext();
        }

        @Override
        public String next() {
            if (it1.hasNext()) {
                return it1.next();
            } else if (it2.hasNext()) {
                return it2.next();
            } else if (it3.hasNext()) {
                return it3.next();
            } else if (it4.hasNext()) {
                return it4.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }


    public Iterator<String> iterator() {
        return new MyIterator();
    }


    public Box(List<String> list1, List<String> list2, List<String> list3, List<String> list4) {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;
    }
}
