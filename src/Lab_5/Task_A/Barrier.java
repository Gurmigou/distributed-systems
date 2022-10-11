package Lab_5.Task_A;

public class Barrier {
    private final int awaitTarget;
    private int awaitCurrent;

    public Barrier(int awaitTarget) {
        this.awaitTarget = awaitTarget;
    }

    public synchronized void await() throws InterruptedException {
        awaitCurrent++;
        if (awaitCurrent >= awaitTarget)
            this.wait();

        awaitCurrent = 0;
        this.notifyAll();
    }
}
