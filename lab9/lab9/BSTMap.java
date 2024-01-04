package lab9;

import java.security.Key;
import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Boshan Chen
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private V lastRemove;
    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int compare = key.compareTo(p.key);
        if (compare == 0) {
            return p.value;
        } else if (compare < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size ++;
            return new Node(key, value);
        }

        int compare = key.compareTo(p.key);
        if (compare == 0) {
            p.value = value;
            return p;
        } else if (compare < 0) {
            p.left = putHelper(key, value, p.left);
            return p;
        } else {
            p.right = putHelper(key, value, p.right);
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<K>();
        for (K k : this) {
            keys.add(k);
        }
        return keys;
    }

    /*Return the Leftest node of the root node p*/
    private Node findRightest(Node p) {
        if (p == null) {
            return null;
        }
        if (p.right == null) {
            return p;
        } else {
            return findRightest(p.right);
        }
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int compare = key.compareTo(p.key);
        if (compare == 0) {
            lastRemove = p.value;
            if (p.left == null && p.right == null){
                size--;
                p = null;
                return p;
            } else if (p.left != null && p.right != null) {
                Node newP = findRightest(p.left);
                V newVal = newP.value;
                K newKey = newP.key;
                p = removeHelper(newP.key, p);
                p.key = newKey;
                p.value = newVal;
                return p;
            } else {
                if (p.right != null) {
                    p.key = p.right.key;
                    p.value = p.right.value;
                    removeHelper(p.key, p.right);
                } else {
                    p.key = p.left.key;
                    p.value = p.left.value;
                    removeHelper(p.key, p.left);
                }
                return p;
            }
        } else if (compare < 0) {
            p.left = removeHelper(key, p.left);
            return p;
        } else {
            p.right = removeHelper(key, p.right);
            return p;
        }
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node h = removeHelper(key, root);
        if (h == null) {
            return null;
        }
        root = h;
        return lastRemove;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<K>{
        private Stack<Node> stack = new Stack<>();
        BSTIterator(Node p) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
        }
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        //@return: return next node
        public K next() {
            Node curt = stack.peek();
            Node node = curt;

            // move to the next node
            if (node.right == null) {
                node = stack.pop();
                while (!stack.isEmpty() && stack.peek().right == node) {
                    node = stack.pop();
                }
            } else {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            return curt.key;
        }
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
    }
}
