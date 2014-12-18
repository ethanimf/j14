package ethan.j14.thread;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.GuardedBy;

/**
 * @since 2014-7-19
 * @author ethan
 */

public class MyConputor<A,V>/* implements Computable<A, V>*/{

	@GuardedBy("this")
	private Map<A, V> cache = new HashMap<A, V>();
	private IComputable<A, V> c;
	
	public  synchronized V  computer(A o) throws InterruptedException{
		//^^ 问题：其他线程严重被阻塞，伸缩性差
		V result = cache.get(o);
		if(result==null){
			result = c.compute(o);
			cache.put(o,result);
		}
			return result;
	}
	
	public MyConputor(IComputable<A,V> c){
		this.c = c;
	}

	public static void main(String[] args) {
		MyExpensiveFunction expensive = new MyExpensiveFunction();
		MyConputor myversion = new MyConputor<String, BigInteger>(expensive);
		try {
			for(int i=0;i<10;i++){
				System.out.println(myversion.computer("555555"));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

 class MyExpensiveFunction implements IComputable<String,BigInteger>{

	@Override
	public BigInteger compute(String str) throws InterruptedException {
		Thread.sleep(1000);
		return new BigInteger(str);
	}
	
}

interface IComputable<A,V>{
	V compute(A argu) throws InterruptedException;
} 