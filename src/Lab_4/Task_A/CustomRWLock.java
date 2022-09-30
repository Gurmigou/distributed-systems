package Lab_4.Task_A;

// Write: a thread can write if and only if there are no read locks and write locks
// Read: a thread can read even with other threads, but it can't read if there is a write lock
public class CustomRWLock {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;

    // Read: unlocked, write: locked
    public synchronized void readLock() {
        while (writers > 0 || writeRequests > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        readers++;
    }

    // Read: unlocked, write: unlocked
    public synchronized void readUnlock() {
        readers--;
        this.notifyAll();
    }

    // Read: locked, write: locked
    public synchronized void writeLock() {
        writeRequests++;

        while (readers > 0 && writers > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        writeRequests--;
        writers++;
    }

    // Read: unlocked, write: unlocked
    public synchronized void writeUnlock() {
        writers--;
        this.notifyAll();
    }
}
