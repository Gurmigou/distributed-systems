package Module_1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

import static Module_1.Task.RANDOM;

public class Customer extends Thread {
    private final int index;
    private static int nextInt = 1;

    {
        index = nextInt;
        nextInt++;
    }

    private final AtomicBoolean gotPhoneCall = new AtomicBoolean(false);
    private final BlockingQueue<Customer> customersQueue;

    public Customer(BlockingQueue<Customer> customersQueue) {
        this.customersQueue = customersQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("#" + index + " Entered the queue");
                customersQueue.add(this);

                int sleep = RANDOM.nextInt(3000);
                Thread.sleep(sleep);

                if (gotPhoneCall.get()) {
                    break;
                } else {
                    boolean present = customersQueue.remove(this);
                    if (!present) {
                        break;
                    }

                    System.out.println("Customer #" + index + " leaved queue");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setGotPhoneCall() {
        this.gotPhoneCall.getAndSet(true);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return index == customer.index;
    }
}
