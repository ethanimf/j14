package v2.j14.jcip.book.test;

import v2.j14.jcip.book.BoundedBuffer;
import v2.j14.jcip.book.PrimeGenerator;
import junit.framework.TestCase;

/**
 * @since 2014-8-13
 * @author ethan
 */

// ^^ 测试中断
public class BoundedBufferTest extends TestCase{

	private static final long LOCKUP_DETECT_TIMEOUT = 2000;

	public void testTakeBlocksWhenEmpty(){
		final BoundedBuffer<Integer> buffer = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread(){
				@Override
				public void run() {
					try {
						buffer.take();  //阻塞
						fail("you should't run to here");
					} catch (InterruptedException success) {
						success.printStackTrace();   //响应中断，说明take被阻塞了
					}
				}
		};
		
		//^^ 还在 运行 ，能不能中断它？  
		//结论:不管线程是不是在阻塞状态，都能被中断
//		taker = new Thread(new PrimeGenerator());
		
			try {
				taker.start();
				Thread.sleep(LOCKUP_DETECT_TIMEOUT);
				taker.interrupt(); //中断
				taker.join(LOCKUP_DETECT_TIMEOUT);//等你回来结账
				assertFalse(taker.isAlive()); //take如果还活着，说明测试失败
			} catch (Exception e) {
				fail("test fail");
			}
		
		
	}
}
