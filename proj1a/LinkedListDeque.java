public class LinkedListDeque<T> {
    public class Node {
        T item;
        Node prev, next;

        public Node(Node prev, Node next, T item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }

    public Node sentinel;
    int size;

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
        if (isEmpty()) {
            Node newNode = new Node(sentinel, sentinel, item);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node newNode = new Node(sentinel, sentinel.next, item);
            sentinel.next = newNode;
        }
        size++;
    }

    public void addLast(T item) {
        if (isEmpty()) {
            Node newNode = new Node(sentinel, sentinel, item);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node newNode = new Node(sentinel, sentinel.next, item);
            sentinel.prev = newNode;
        }
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
            System.out.print(tmp.item + " ");
        }
    }

    public T removeFirst() {
        if (!isEmpty()) {
            --size;
            Node tmp = sentinel.next;
            sentinel.next = tmp.next;
            return tmp.item;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (!isEmpty()) {
            --size;
            Node tmp = sentinel.prev;
            sentinel.prev = tmp.prev;
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

    public T getRecursiveImplement(int index, Node node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveImplement(index - 1, node.next);
    }

    /**
     * Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (index >= 0 && index <= size - 1) {
            return getRecursiveImplement(index, sentinel.next);
        }
        return null;
    }


}
