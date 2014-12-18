package v2.j14.jcip.book;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static java.util.concurrent.TimeUnit.SECONDS;
/**
 * BrokenPrimeProducer
 * <p/>
 * Unreliable cancellation that can leave producers stuck in a blocking operation
 *
 * @author Brian Goetz and Tim Peierls
 */
class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled){
            	//^^ queue已经被阻塞，不用cancel动手了
                queue.put(p = p.nextProbablePrime());
                System.out.println(p);
            }
        } catch (InterruptedException consumed) {
        }
    }

    public void cancel() {
        cancelled = true;
    }
    
    public static void main(String[] args) {
    	BrokenPrimeProducer bpp = new BrokenPrimeProducer(new ArrayBlockingQueue<BigInteger>(3));
    	bpp.start();
    	try {
			SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bpp.cancel();
		}
	}
}

