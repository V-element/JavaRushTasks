package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    int size;
    private Map<String , Entry> tree = new HashMap<>();

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        void checkChildren() {
            if(leftChild != null){
                availableToAddLeftChildren = false;
            }
            if(rightChild != null){
                availableToAddRightChildren = false;
            }
        }

    }

    public CustomTree() {
        this.root = new Entry<>("0");
        tree.put("0", this.root);
    }

    @Override
    public boolean add(String s) {
        Entry<String> top = root;
        Entry<String> current = new Entry(s);
        Queue<Entry> queue = new LinkedList<>();
        queue.add(top);
        boolean makeAvailableToAdd = false;

        while (!queue.isEmpty()) {
            top = queue.element();
            if (top.leftChild != null) {
                queue.add(top.leftChild);
            } else if (top.availableToAddLeftChildren) {
                top.leftChild = current;
                top.leftChild.parent = top;
                size++;
                return true;
            } else if (makeAvailableToAdd) {
                top.availableToAddLeftChildren = true;
                top.leftChild = current;
                top.leftChild.parent = top;
                size++;
                return true;
            }
            if (top.rightChild != null) {
                queue.add(top.rightChild);
            } else if (top.availableToAddRightChildren) {
                top.rightChild = current;
                top.rightChild.parent = top;
                size++;
                return true;
            } else if (makeAvailableToAdd) {
                top.availableToAddRightChildren = true;
                top.rightChild = current;
                top.rightChild.parent = top;
                size++;
                return true;
            }
            queue.poll();
            if (queue.isEmpty()) {
                makeAvailableToAdd = true;
                queue.add(root);
            }
        }
        queue.clear();
        return false;
    }

    public String getParent(String s) {
        Entry<String> top = root;
        Queue<Entry> queue = new LinkedList<>();
        queue.add(top);
        while (!queue.isEmpty()) {
            top = queue.element();
            if (top.leftChild != null) {
                if (top.leftChild.elementName.equals(s)) {
                    return top.leftChild.parent.elementName;
                } else {
                    queue.add(top.leftChild);
                }
            }
            if (top.rightChild != null) {
                if (top.rightChild.elementName.equals(s)) {
                    return top.rightChild.parent.elementName;
                } else {
                    queue.add(top.rightChild);
                }
            }
            queue.poll();
        }
        queue.clear();
        return null;
    }

    @Override
    public boolean remove(Object o) {

        if (o instanceof String) {
            Entry<String> top = root;
            Queue<Entry> queue = new LinkedList<>();
            queue.add(top);
            while (!queue.isEmpty()) {
                top = queue.element();
                if (top.elementName.equals(o)) {
                   //size--;
                   if (top.leftChild != null) {
                       remove(top.leftChild.elementName);
                   }
                   if (top.rightChild != null) {
                       remove(top.rightChild.elementName);
                   }
                   top = null;

                } else {
                    if (top.leftChild != null) {
                        if (top.leftChild.elementName.equals(o)) {
                            top.availableToAddLeftChildren = false;
                            top.leftChild = null;
                            //remove(top.leftChild.elementName);
                            //size--;
                            //return true;
                        } else {
                            queue.add(top.leftChild);
                        }
                    }
                    if (top.rightChild != null) {
                        if (top.rightChild.elementName.equals(o)) {
                            top.availableToAddRightChildren = false;
                            top.rightChild = null;
                            //remove(top.rightChild.elementName);
                            //size--;
                            //return true;
                        } else {
                            queue.add(top.rightChild);
                        }
                    }
                }
                queue.poll();
            }
            queue.clear();

            size = 0;
            countChild(root);
            return true;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void countChild(Entry element) {
        //int countChild = 0;
        if (element.leftChild != null) {
            size++;
            countChild(element.leftChild);
        }
        if (element.rightChild != null) {
            size++;
            countChild(element.rightChild);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }


}