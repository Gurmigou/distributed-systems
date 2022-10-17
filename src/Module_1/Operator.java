package Module_1;

import java.util.concurrent.BlockingQueue;

public class Operator extends Thread {
    private final int index;
    private static int nextInt = 1;

    {
        index = nextInt;
        nextInt++;
    }

    private final BlockingQueue<Customer> customersQueue;

    public Operator(BlockingQueue<Customer> customersQueue) {
        this.customersQueue = customersQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Customer customer = customersQueue.take();
                customer.setGotPhoneCall();

                System.out.println("Client #" + customer.getIndex() + " is served by operator #" + index);

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
