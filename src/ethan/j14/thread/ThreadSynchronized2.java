/**
 *
 */
package ethan.j14.thread;


/**
 * @author ethan
 * @version $Id$
 *          ^^坑同步问题
 * @since 2013-10-17
 */

public class ThreadSynchronized2 {
    public static void main(String[] args) {
        new ThreadSynchronized2().init();
    }

    private void init() {
        final People outputer = new People();
        final Man outputer2 = new Man();
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {

                @Override
                public void run() {
                    outputer2.output();
                }
            }).start();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    outputer2.output();
                }
            }).start();
        }
    }

    // 内部类的好处：可以访问外部实例对象的变量
    // 加上static就是外部类了
    class People {
        public void output() {
            synchronized (this.getClass()) {
                System.out.println(this.getClass());  //class ethan.v4.thread.ThreadSynchronized2$Man
                System.out.println("人类");
            }
        }
    }

    class Man extends People {

        public void output() {
            synchronized (this.getClass()) {
                System.out.println(this.getClass());  //class ethan.v4.thread.ThreadSynchronized2$Man
                System.out.println("男人");
                super.output();   //^^如果内部锁不是可重入的，这里将会死锁
                System.out.println("=================================");
            }
        }

    }
}
