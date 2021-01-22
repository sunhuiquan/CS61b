public class LinkedListDeque<T> {
    private class Node {
        T item;
        Node prev, next;

        public Node(Node prev, Node next, T item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    private Node sentinel;
    private int size;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node newNode = new Node(sentinel, sentinel.next, item);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    public void addLast(T item) {
        Node newNode = new Node(sentinel.prev, sentinel, item);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (Node tmp = sentinel.next; tmp != sentinel; tmp = tmp.next) {
            if (tmp.next == sentinel) {
                System.out.print(tmp.item + "\n");
                break;
            }
            System.out.print(tmp.item + " ");
        }
    }

    public T removeFirst() {
        if (!isEmpty()) {
            size--;
            Node tmp = sentinel.next;
            sentinel.next = tmp.next;
            sentinel.next.prev = sentinel;
            return tmp.item;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (!isEmpty()) {
            size--;
            Node tmp = sentinel.prev;
            sentinel.prev = tmp.prev;
            sentinel.prev.next = sentinel;
            return tmp.item;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (index >= 0 && index <= size - 1) {
            Node tmp = sentinel.next;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            return tmp.item;
        }
        return null;
    }

    private T getRecursive(int index, Node node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursive(index - 1, node.next);
    }

    /**
     * Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (index >= 0 && index <= size - 1) {
            return getRecursive(index, sentinel.next);
        }
        return null;
    }
}
