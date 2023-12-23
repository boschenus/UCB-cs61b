public class ArrayDeque<item> implements Deque<item>{
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

    private int getFirst() {
        if (nextFirst == items.length - 1) {
            return 0;
        } else {
            return nextFirst + 1;
        }
    }
    private void resize(int c) {
        item[] a = (item[]) new Object[c];
        if (getFirst() + size > items.length) {
            System.arraycopy(items, getFirst(), a, a.length - items.length + getFirst(), items.length - getFirst());
            System.arraycopy(items,0, a, 0, nextLast);
            if (a.length - (items.length - getFirst()) == 0) {
                nextFirst = a.length - 1;
            } else {
                nextFirst = a.length - (items.length - getFirst()) - 1;
            }
        } else {
            System.arraycopy(items, getFirst(), a, 0, size);
            nextFirst = a.length - 1;
        }
        nextLast = calnextLast(a, size, nextFirst);
        items = a;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public void addLast(item item) {
        if (items.length == size) {
            resize(2*size + 1);
        }

        items[nextLast] = item;
        size++;

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }
    }
    @Override
    public item get(int index) {
        if (nextFirst + 1 + index > items.length - 1) {
            return items[nextFirst + index + 1 - items.length];
        } else {
            return items[nextFirst + 1 + index];
        }
    }
    @Override
    public item removeLast() {
        if (isEmpty()) {
            return null;
        }

        item l = get(size - 1);
        size--;

//        nextLast = calnextLast(items, size, nextFirst);
        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }

        items[nextLast] = null;
        if (size < items.length/4) {
            double k = 0.5*items.length;
            resize((int)k + 1);
        }

        return l;
    }
    @Override
    public void printDeque() {
        for (int i=0; i<size; i++) {
            System.out.print(String.valueOf(get(i)) + " ");
        }
    }
    @Override
    public void addFirst(item item) {
        if (items.length == size) {
            resize(2*size + 1);
        }

        items[nextFirst] = item;
        size++;
        if (nextFirst == 0) {
            nextFirst = items.length-1;
        } else {
            nextFirst--;
        }
    }
    @Override
    public item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        item f = get(0);
        size--;

        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst = nextFirst + 1;
        }
        items[nextFirst] = null;

        if (size < items.length/4) {
            double k = 0.5*items.length;
            resize((int)k + 1);
        }
        return f;
    }
    @Override
    public int size() {
        return size;
    }

}
