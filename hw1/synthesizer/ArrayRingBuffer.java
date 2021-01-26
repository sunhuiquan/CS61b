package synthesizer;

//import java.util.Iterator;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last++] = x;
        if (last == capacity) {
            last = 0;
        }
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then throw
     * new RuntimeException("Ring buffer underflow"). Exceptions covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T res = rb[first++];
        if (first == capacity) {
            first = 0;
        }
        fillCount--;
        return res;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

//    /**
//     * Adds x to the end of the ring buffer. If there is no room, then
//     * throw new RuntimeException("Ring buffer overflow"). Exceptions
//     * covered Monday.
//     */
//    @Override
//    public void enqueue(T x) {
//        if (isFull()) {
//            throw new RuntimeException("Ring buffer overflow");
//        }
//        last = (last + 1) % capacity;
//        rb[last] = x;
//        fillCount++;
//    }
//
//    /**
//     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
//     * throw new RuntimeException("Ring buffer underflow"). Exceptions
//     * covered Monday.
//     */
//    @Override
//    public T dequeue() {
//        if (isEmpty()) {
//            throw new RuntimeException("Ring buffer underflow");
//        }
//        first = (first + 1) % capacity;
//        fillCount--;
//        return rb[first];
//    }
//
//    /**
//     * Return oldest item, but don't remove it.
//     */
//    @Override
//    public T peek() {
//        if (isEmpty()) {
//            throw new RuntimeException("Ring buffer underflow");
//        }
//        return rb[(first + 1) % capacity];
//    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int ptr;

        public ArrayRingBufferIterator() {
            ptr = first;
        }

        @Override
        public boolean hasNext() {
            return (ptr != last && isFull());
        }

        @Override
        public T next() {
            return rb[++ptr];
        }
    }
}
