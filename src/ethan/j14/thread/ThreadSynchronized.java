/**
 * 
 */
package ethan.j14.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @since 2013-10-17
 * @author ethan
 * @version $Id$ 
 * ^^坑同步问题
 */

public class ThreadSynchronized {
	public static void main(String[] args) {
		new ThreadSynchronized().init();
	}

	private void init() {
		final Outputer outputer = new Outputer();
//		long start = System.currentTimeMillis();
		while (true) {
			
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					outputer.output2("zhaoxiaoxiang");
//					outputer.getCount();
				}
			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {
					outputer.output2("ethanicenet");
//					outputer.getCount();
				}
			}).start();
		}
//		long end = System.currentTimeMillis();
//		double spend = (end-start)/1000;
//		System.out.println("spend:"+spend+"s");
	}

	// 内部类的好处：可以访问外部实例对象的变量
	// 加上static就是外部类了
	static class Outputer {
		private AtomicLong atomic = new AtomicLong(0);
		private int[] lock = new int[0];
		public void output(String name) {
			int len = name.length();
			//使用字节码对象作为同步锁
//			synchronized (this.getClass()) {
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
//			}
		}

		public  void output2(String name) {
			//使用this作为同步锁
//			synchronized (this) {
			synchronized (lock) {
				int len = name.length();
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
			
		}
		
		//使用修饰符作为同步
		public  synchronized void output3(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		//使用计数器同步(暂时失效)
		public void output4(String name){
			atomic.incrementAndGet();
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
//			System.out.println(atomic.get());
		}
		
		public  long getCount(){
			System.out.println(Thread.currentThread().getName()+"--"+atomic.get());
			atomic.incrementAndGet();
			return 0;
		}
	}
}
