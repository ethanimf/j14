package ethan.j14.thread;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import v2.j14.jcip.book.LaunderThrowable;

/**
 * @since 2014-7-19
 * @author ethan
 */

public class MyConputorV4<A,V>/* implements Computable<A, V>*/{

	private ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private IComputable<A, V> c;
	
	public   V  compute(final A o) throws InterruptedException{
		Future<V> f = cache.get(o);
		while(true){
			if(f==null){  
				Callable<V> mCallback = new Callable<V>() {
					@Override
					public V call() throws Exception {
						return  c.compute(o);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(mCallback);
				f = ft;
//				cache.put(o,f);
				cache.putIfAbsent(o, ft);   // ^^ 原子化的“缺少即加入”，解决非原子的“检查再运行”
				ft.run();
			}
			try {
				return f.get(0,TimeUnit.SECONDS);
			} catch (ExecutionException e) {
				 throw LaunderThrowable.launderThrowable(e.getCause());
			}catch(TimeoutException e){
				e.printStackTrace();
			}
		}
	}
	
	public MyConputorV4(IComputable<A,V> c){
		this.c = c;
	}

	public static void main(String[] args) {
		ComputableImpl expensive = new ComputableImpl();
		MyConputorV4 myversion = new MyConputorV4<String, BigInteger>(expensive);
		try {
			for(int i=0;i<10;i++){
				System.out.println(myversion.compute("555555"));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


	
