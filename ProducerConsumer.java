import java.util.*;

/**
 * For producer-consumer problem, you need two synchronization techniques.
 * 1. A lock around the shared buffer to ensure consistency of the buffer.
 * 2. A synchronization primitive to ensure the right order of reading and writing.
 * This solution uses a monitor for 1 and wait/notify for 2.
 */
class ProducerConsumer {

    int N = 10;
    int[] buffer = new int[N];
    int count = 0;
    int read = 0;
    int write = 0;

    /**
     * wait() and notify() help synchronization between two threads trying to insert
     * and remove. It ensures that when the buffer is full, the producer waits for it
     * to have atleast one free slot. And ensures that when the buffer is empty, the
     * consumer waits for atleast one item to be written to the buffer.
     */
    public static void main(String[] args) {
        ProducerConsumer p = new ProducerConsumer();
        Producer producer = p.new Producer();
        Consumer consumer = p.new Consumer();
        producer.start();
        consumer.start();
    }

    // synchronized is a monitor that makes sure only one thread
    // at a time is calling any synchronized method of this class.
    // This gurantees that count, buffer etc won't be modified by any
    // other thread during the execution of this method.
    synchronized void insert(int item) {
        if (count == N-1) {
            sleep();
        }
        buffer[write] = item;
        count++;
        write = (write + 1) % N;
        if (count == 1) {
            notify();
        }
    }

    synchronized void remove() {
        if (count == 0) {
            sleep();
        }
        System.out.println(buffer[read]);
        count--;
        read = (read + 1) % N;
        if (count == N-2) {
            notify();
        }
    }

    void sleep() {
        try {
            wait();
        } catch (InterruptedException e) {
            
        } 
    }

    class Producer extends Thread {
        
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    insert(i);
                }
            }
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                remove();
            }
        }
    }
}