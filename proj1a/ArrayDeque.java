public class ArrayDeque<T> {
    private T[] array;
    private int head, tail;
    private int capacity;

    public ArrayDeque() {
        capacity = 8;
        array = (T[]) new Object[capacity];
        head = tail = 0;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    private boolean isFull() {
        return (tail + 1) % capacity == head;
    }

    private void increaseCapacity() {
        T[] newArray = (T[]) new Object[capacity * 2];
        if (head < tail) {
            int size = tail - head;
            System.arraycopy(array, head, newArray, 0, size + 1);
            head = 0;
            tail = size;
        } else if (head > tail) {
            int size = tail + capacity - head;
            System.arraycopy(array, head, newArray, 0, capacity - head);
            System.arraycopy(array, 0, newArray, capacity - head, size - (capacity - head) + 1);
            head = 0;
            tail = size;
        }
        capacity *= 2;
        array = newArray;
    }

    private void decreaseCapacity() {
        T[] newArray = (T[]) new Object[capacity / 2];
        if (head < tail) {
            int size = tail - head;
            System.arraycopy(array, head, newArray, 0, size + 1);
            head = 0;
            tail = size;
        } else if (head > tail) {
            int size = tail + capacity - head;
            System.arraycopy(array, head, newArray, 0, capacity - head);
            System.arraycopy(array, 0, newArray, capacity - head, size - (capacity - head) + 1);
            head = 0;
            tail = size;
        } else {
            head = tail = 0;
        }
        capacity /= 2;
        array = newArray;
    }

    public void addFirst(T item) {
        if (isFull()) {
            increaseCapacity();
            addFirst(item);
        } else {
            array[head] = item;
            head = (head - 1 + capacity) % capacity;
        }
    }

    public void addLast(T item) {
        if (isFull()) {
            increaseCapacity();
            addLast(item);
        } else {
            tail = (tail + 1 + capacity) % capacity;
            array[tail] = item;
        }
    }

    private boolean isLess() {
        int a = head, b = tail;
        if (b < a) {
            b += capacity;
        }
        if (b - a < 0.25 * capacity) {
            return true;
        }
        return false;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        head = (head + 1) % capacity;
        T tmp = array[head];
        if (isLess()) {
            decreaseCapacity();
        }
        return tmp;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T tmp = array[tail];
        tail = (tail - 1 + capacity) % capacity;
        if (isLess()) {
            decreaseCapacity();
        }
        return tmp;
    }

    public T get(int index) {
        int pos = (index + head + 1) % capacity;
        if (index < 0 || index >= size() || isEmpty()) {
            return null;
        }
        return array[pos];
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        } else if (tail > head) {
            return tail - head;
        }
        return tail - head + capacity;
    }

    public void printDeque() {
        for (int i = (head + 1) % capacity; i != tail; i = (i + 1) % capacity) {
            System.out.print(array[i] + " ");
        }
        System.out.print(array[tail] + "\n");
    }
}
