package ethan.j14.thread;

/**
 * @author ethan
 * @since 2014-7-16
 */

public class NoVisibility {

    private static boolean ready;
    private static int number;

    public static void main(String[] args) {

        new ReaderThead().start();
        try {
            sleep();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        number = 40;
        ready = true;

    }

    private static void sleep() throws InterruptedException {
        Thread.sleep(5000);
    }

    private static class ReaderThead extends Thread {
        @Override
        public void run() {
            System.out.println("reader thread start..");
            while (!ready)
                Thread.yield();
            System.out.println(number);

        }
    }
}
