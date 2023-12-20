public class ArrayDeque<item> {
    private item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (item[]) new Object[10];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    private int calnextLast(item[] p, int s, int nF) {
        if (nF + 1 + s >= p.length) {
            return nF + s + 1 - p.length;
        } else {
            return nF + s + 1;
        }
    }

    private void resize(int c) {
        item[] a = (item[]) new Object[c];
        if (nextFirst + 1 + size > items.length) {
            System.arraycopy(items,nextFirst + 1, a, a.length - (items.length - nextFirst - 1), items.length - nextFirst - 1);
            System.arraycopy(items,0, a, 0, nextLast);
            nextFirst = a.length - (items.length - nextFirst);
        } else {
            System.arraycopy(items,nextFirst+1, a, 0, size);
            nextFirst = a.length - 1;
        }
        nextLast = calnextLast(a, size, nextFirst);
        items = a;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(item item) {
        if (items.length == size) {
            resize(2*size);
        }

        items[nextLast] = item;
        size++;
        nextLast = calnextLast(items, size, nextFirst);
    }

    public item get(int index) {
        if (nextFirst + 1 + index > items.length - 1) {
            return items[nextFirst + index + 1 - items.length];
        } else {
            return items[nextFirst + 1 + index];
        }
    }
    public item removeLast() {
        if (isEmpty()) {
            return null;
        }

        item l = get(size-1);
        size--;

        if (size < items.length/4) {
            double k = 0.5*items.length;
            resize((int)k);
        }

        nextLast = calnextLast(items, size, nextFirst);
        items[nextLast] = null;
        return l;
    }

    public void printDeque() {
        for (int i=0; i<size; i++) {
            System.out.print(String.valueOf(get(i)) + " ");
        }
    }

    public void addFirst(item item) {
        if (items.length == size) {
            resize(2*size);
        }

        items[nextFirst] = item;
        size++;
        if (nextFirst == 0) {
            nextFirst = items.length-1;
        } else {
            nextFirst--;
        }
    }

    public item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        item f = get(0);
        size--;

        if (nextFirst == items.length-1) {
            nextFirst = 0;
        } else {
            nextFirst = nextFirst+1;
        }

        if (size < items.length/4) {
            double k = 0.5*items.length;
            resize((int)k);
        }

        items[nextFirst] = null;
        return f;
    }

    public int size() {
        return size;
    }
}
