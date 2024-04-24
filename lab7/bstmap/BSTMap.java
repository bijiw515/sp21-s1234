package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable , V> implements Map61B {
    private K key;
    private V value;
    private BSTMap left;
    private BSTMap right;
    private int size;

    @Override
    public void clear() {
        this.key = null;
        this.value = null;
        this.size = 0;
        if (this.left != null && this.right == null){
            this.left.clear();
        } else if (this.right != null && this.left == null) {
            this.right.clear();
        } else if (this.right != null && this.left != null) {
            this.left.clear();
            this.right.clear();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (this.key == null){
            return false;
        }
        else if (this.key.equals(key)) {
            return true;
        } else if (this.key.compareTo(key) < 0) {
            return this.left.containsKey(key);
        } else if (this.key.compareTo(key) > 0) {
            return this.right.containsKey(key);
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        if (this.key == null){
            return null;
        }
        else if (this.key.equals(key)) {
            return this.value;
        } else if (this.key.compareTo(key) < 0) {
            return this.left.get(key);
        } else if (this.key.compareTo(key) > 0) {
            return this.right.get(key);
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(Object key, Object value) {
        if (this.key == null){
            this.key = (K) key;
            this.value = (V) value;
        }
        if (this.left == null && this.key.compareTo(key) < 0) {
            this.left = new BSTMap();
            this.left.key = (K) key;
            this.left.value = (V) value;
        }else if (this.right == null && this.key.compareTo(key) > 0) {
            this.right = new BSTMap();
            this.right.key = (K) key;
            this.right.value = (V) value;
        } else if (this.key.compareTo(key) < 0) {
            this.left.put(key, value);
        } else if (this.key.compareTo(key) > 0) {
            this.right.put(key, value);
        }
        this.size += 1;
    }
    @Override
    public Set keySet () {
        throw new UnsupportedOperationException();
    }
    @Override
    public Object remove (Object key){
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove (Object key, Object value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator () {
        throw new UnsupportedOperationException();
    }
}