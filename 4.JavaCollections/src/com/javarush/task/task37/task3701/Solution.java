package com.javarush.task.task37.task3701;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/* 
Круговой итератор
*/
public class Solution<T> extends ArrayList<T> {
    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }


    }

    public class RoundIterator implements Iterator<T> {
        ListIterator<T> listIterator = Solution.super.listIterator();

        @Override
        public void remove() {
            listIterator.remove();
        }

        @Override
        public boolean hasNext() {
            if (!listIterator.hasNext()) {
                listIterator = Solution.super.listIterator();
            }
            return listIterator.hasNext();
        }

        @Override
        public T next() {
            return listIterator.next();
        }
    }
}
