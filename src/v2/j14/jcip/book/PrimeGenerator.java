package v2.j14.jcip.book;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * PrimeGenerator
 * <p/>
 * Using a volatile field to hold cancellation state
 *
 * @author Brian Goetz and Tim Peierls
 */

// ^^ 计算素数1秒钟
@ThreadSafe
public class PrimeGenerator implements Runnable {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @GuardedBy("this") private final List<BigInteger> primes
            = new ArrayList<BigInteger>();
    private volatile boolean cancelled;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            System.out.println(p);
            synchronized (this) {
                primes.add(p);
            }
        }
        System.exit(0);
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        exec.execute(generator);
        try {
//            SECONDS.sleep(1);
        } finally {
//            generator.cancel();
        }
        return generator.get();
    }
    
    public static void main(String[] args) {
		try {
			PrimeGenerator.aSecondOfPrimes();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
