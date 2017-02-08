package ethan.j14.thread;

class MyThead implements Runnable {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("thread======>" + Thread.currentThread().getName());
            if (i >= 90) {
                Thread.yield();
                System.out.println(i + "     让路 给thread======>" + Thread.currentThread().getName());
            }
        }
    }
}

public class TheadGiveWay {
    public static void main(String[] args) {
        MyThead myThead = new MyThead();
        Thread thread1 = new Thread(myThead, "AAA");
        Thread thread2 = new Thread(myThead, "BBB");
        thread1.start();
        thread2.start();
    }
}
