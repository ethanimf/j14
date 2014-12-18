package ethan.j14.thread;

import net.jcip.annotations.GuardedBy;

/**
 * @since 2014-7-16
 * @author ethan
 */

public class SynachronizedInt {

	@GuardedBy("this")
	private volatile int money;

	public synchronized int getMoney() {
		return money;
	}

	public synchronized void setMoney(int money) {
		
		this.money = money;
	}

	public static void main(String[] args) {
		final SynachronizedInt sint = new SynachronizedInt();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
					int money = sint.getMoney();
					if(money ==0) Thread.yield();
					System.out.println(Thread.currentThread().getName()+" get "+sint.getMoney());
				
			}
		}, "getThread").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
					sint.setMoney(100);
					System.out.println(Thread.currentThread().getName()+" set "+100);
			}
		}, "setThread").start();
	}
}
