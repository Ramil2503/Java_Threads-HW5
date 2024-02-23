import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(6);

        Fork fork1 = new Fork("Fork1");
        Fork fork2 = new Fork("Fork2");
        Fork fork3 = new Fork("Fork3");
        Fork fork4 = new Fork("Fork4");
        Fork fork5 = new Fork("Fork5");

        Thread philosopher1 = new Thread(new Philosopher("Philosopher1", fork1, fork2, cdl));
        Thread philosopher2 = new Thread(new Philosopher("Philosopher2", fork2, fork3, cdl));
        Thread philosopher3 = new Thread(new Philosopher("Philosopher3", fork3, fork4, cdl));
        Thread philosopher4 = new Thread(new Philosopher("Philosopher4", fork4, fork5, cdl));
        Thread philosopher5 = new Thread(new Philosopher("Philosopher5", fork5, fork1, cdl));

        philosopher1.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();
        philosopher5.start();

        while (cdl.getCount() != 1) {
            Thread.sleep(100);
        }

        System.out.println("-------------------------DINNER OFFICIALLY OPENED-------------------------");
        cdl.countDown();
    }
}
