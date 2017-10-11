import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Producer-Consumer using semaphores for mutex and synchronization.
 */
class ProdConSemaphore {

    private static int N = 10;
    int[] buffer = new int[N];
    int read = 0;
    int write = 0;

    Semaphore empty = new Semaphore(N);
    Semaphore full = new Semaphore(N);
    Semaphore mutex = new Semaphore(1);

    /**
     * In the beginning the buffer is all empty. So the full semaphore is
     * completely drained. The empty semaphore has N permits to hand out.
     */
    public static void main(String[] args) {
        ProdConSemaphore p = new ProdConSemaphore();
        p.full.drainPermits();
        Producer producer = p.new Producer();
        Consumer consumer = p.new Consumer();
        producer.start();
        consumer.start(); 
    }

    /**
     * Only the producer can release full semaphores into the pool.
     * The producer has to wait for atleast one empty slot to be able to
     * write something to the buffer.
     */
    class Producer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                try {
                empty.acquire();
                mutex.acquire();
                buffer[write] = i;
                write = (write + 1) % N;
                mutex.release();
                full.release();
                } catch (InterruptedException e) {

                }
            }
        }
    }

    /**
     * Only the consumer can release empty semaphores into the pool.
     * The consumer has to wait for atleast one full slot to be able to
     * read from the buffer.
     */
    class Consumer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                try {
                full.acquire();
                mutex.acquire();
                System.out.println(buffer[read]);
                read = (read + 1) % N;
                mutex.release();
                empty.release();
                } catch (InterruptedException e) {

                }
            }
        }
    }

}