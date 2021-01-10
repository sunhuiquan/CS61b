public class ArrayDeque<T> {
    T[] array;
    int head, tail;
    int capacity;

    public ArrayDeque() {
        capacity = 8;
        array = (T[]) new Object[capacity];
        head = tail = 0;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return (head + 1) % capacity == tail;
    }

    public void increaseCapacity() {
        T[] newArray = (T[]) new Object[capacity * 2];
        System.arraycopy(array, 0, newArray, 0, capacity);
        capacity = capacity * 2;
        array = newArray;
    }

    public void decreaseCapacity() {
        T[] newArray = (T[]) new Object[capacity / 2];
        if (head < tail) {
            System.arraycopy(array, head, newArray, 0, tail - head + 1);
            head = 0;
            tail -= head;
        } else if (head > tail) {
            int size = tail + capacity - head;
            System.arraycopy(array, head, newArray, 0, capacity - head);
            System.arraycopy(array, 0, newArray, capacity - head, size - (capacity - head) + 1);
            head = 0;
            tail = size;
        } else {
            head = tail = 0;
        }
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
            addFirst(item);
        } else {
            tail = (tail - 1 + capacity) % capacity;
            array[tail] = item;
        }
    }

    boolean isLess() {
        int a = head, b = tail;
        if (b < a)
            b += capacity;
        if (b - a < 0.25 * capacity)
            return true;
        return false;
    }

    public T removeFirst() {
        head = (head + 1) % capacity;
        T tmp = array[head];
        if (isLess()) {
            decreaseCapacity();
        }
        return tmp;
    }

    public T removeLast() {
        T tmp = array[tail];
        tail = (tail + 1) % capacity;
        if (isLess()) {
            decreaseCapacity();
        }
        return tmp;
    }


    public T get(int index) {
        if (index > head && index <= tail) {
            return array[index];
        }
        return null;
    }

    public void printDeque() {
        for (int i = (head + 1) % capacity; i != tail; i = (i + 1) % capacity) {
            System.out.print(array[i] + " ");
        }
        System.out.print(array[tail] + "\n");
    }
}
