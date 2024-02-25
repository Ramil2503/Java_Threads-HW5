import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher implements Runnable {
    private String name;
    private Fork forkA;
    private Fork forkB;
    private CountDownLatch cdl;
    private int cnt = 3;

    public Philosopher(String name, Fork forkA, Fork forkB, CountDownLatch cdl) {
        this.name = name;
        this.forkA = forkA;
        this.forkB = forkB;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            getReady();
            cdl.await();
            eatingLoop();
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getReady() throws InterruptedException {
        Thread.sleep(1000 + new Random().nextInt(100, 2000));
        cdl.countDown();
        System.out.println(name + " is ready");
    }

    private void eatingLoop() throws InterruptedException {
        while (cnt > 0) {
            eating();
            Thread.sleep(100);
        }
    }

    private synchronized void eating() throws InterruptedException {
        Thread.sleep(500);
        if (forkA.isAvailable() && forkB.isAvailable()) {
            forkA.setAvailable(false);
            forkB.setAvailable(false);
            System.out.println(name + " began eating. Using forks: " + forkA.getName() + ", " + forkB.getName());
            Thread.sleep(1000 + new Random().nextInt(100, 2000));
            cnt--;
            System.out.println(name + " ate enough and now resting. Puting back forks: " + forkA.getName() + ", "
                    + forkB.getName());
            forkA.setAvailable(true);
            forkB.setAvailable(true);
        }
    }

    private void finish() {
        System.out.println(name + " finished eating. And leaves the table!");
    }
}
