package ethan.j14.thread;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import v2.j14.jcip.book.LaunderThrowable;

/**
 * @since 2014-7-19
 * @author ethan
 */

public class MyConputorV3<A,V>/* implements Computable<A, V>*/{

	private Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private IComputable<A, V> c;
	
	public   V  compute(final A o) throws InterruptedException{
		// ^^ future.get解决了重复计算的问题
		Future<V> f = cache.get(o);
		if(f==null){  // ^^ 非原子的“检查再运行” 问题
			Callable<V> mCallback = new Callable<V>() {
				@Override
				public V call() throws Exception {
					return  c.compute(o);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(mCallback);
			f = ft;
			cache.put(o,f);
			ft.run();
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			 throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}
	
	public MyConputorV3(IComputable<A,V> c){
		this.c = c;
	}

	public static void main(String[] args) {
		ComputableImpl expensive = new ComputableImpl();
		MyConputorV3 myversion = new MyConputorV3<String, BigInteger>(expensive);
		try {
			for(int i=0;i<10;i++){
				System.out.println(myversion.compute("555555"));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


	
