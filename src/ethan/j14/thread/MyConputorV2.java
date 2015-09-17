package ethan.j14.thread;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 2014-7-19
 * @author ethan
 *
 * st from mac
 */

public class MyConputorV2<A,V>/* implements Computable<A, V>*/{

	private Map<A, V> cache = new ConcurrentHashMap<A, V>();
	private IComputable<A, V> c;
	
	public   V  computer(A o) throws InterruptedException{
		// ^^ 问题，计算可能被重复执行
		V result = cache.get(o);
		if(result==null){
			result = c.compute(o);
			cache.put(o,result);
		}
			return result;
	}
	
	public MyConputorV2(IComputable<A,V> c){
		this.c = c;
	}

	public static void main(String[] args) {
		ComputableImpl expensive = new ComputableImpl();
		MyConputorV2 myversion = new MyConputorV2<String, BigInteger>(expensive);
		try {
			for(int i=0;i<10;i++){
				System.out.println(myversion.computer("555555"));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}


	
