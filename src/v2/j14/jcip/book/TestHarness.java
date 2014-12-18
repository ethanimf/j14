package v2.j14.jcip.book;

import java.util.Random;
import java.util.concurrent.*;

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();  //吹哨员 预备 ->1
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();  //------------------------> 所有赛跑选收都跑完终点      5
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }//----------------------------------------------------------> 所有赛跑选收都各就各位  2 

        long start = System.nanoTime();
        startGate.countDown();  //开始跑 ->3
        endGate.await();//计时员等着 ->4
        long end = System.nanoTime();// ---------------->  计时员记时   6
        return end - start;
    }
    
    
    public static void main(String[] args) {
    	int nThreads = Runtime.getRuntime().availableProcessors();
    	Runnable task = new Runnable() {
			
			@Override
			public void run() {
				Random random = new Random();
				System.out.println(random.nextLong());
			}
		};
		
		TestHarness test = new TestHarness();
		try {
			long time = test.timeTasks(nThreads, task);
			System.out.println(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
