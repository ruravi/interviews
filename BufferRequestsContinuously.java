import java.util.concurrent.*;
import java.util.*;

class BufferRequestsContinuously {

	private static final int N = 5;

	public static void main(String[] args) throws Exception {
		Consumer consumer = new Consumer();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(N,
			new Runnable() {
				public void run() {
					System.out.println("Barrier broken");
					consumer.process();
				}
			});

		ExecutorService executorService = Executors.newFixedThreadPool(5);

		Random rand = new Random();
		for (int i = 0; i < 5; i++) {
			executorService.submit(
				new Runnable() {
					public void run() {
						int n = rand.nextInt(10);
						try {
							Thread.sleep(n * 1000);
							System.out.println("Enqueuing...");
							consumer.enqueue(10);
							cyclicBarrier.await(5, TimeUnit.SECONDS);
						} catch (Exception e) {
							
						}
						System.out.println("Tired of waiting...");
						consumer.process();
					}
				});
		}
	}
	
}

class Consumer {

	private Queue<Integer> requests = new ArrayDeque<>();

	public void enqueue(int a) {
		requests.offer(a);
	}

	public void process() {
		System.out.println("Processing " + requests.size() + " requests");
		while (!requests.isEmpty()) {
			requests.poll();
		}
	}

}