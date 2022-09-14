package Lab_2;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Task_B {
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        final int CAPACITY = 5;
        final int NUM_DETAILS = 35;

        ProducerConsumerCounter pcc = new ProducerConsumerCounter(CAPACITY);

        Thread ivanovThread = new Thread(() -> {
            try {
                pcc.produce(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread petrovThread = new Thread(() -> {
            try {
                pcc.consume(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread nechiporchukThread = new Thread(() -> {
            try {
                pcc.count(NUM_DETAILS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ivanovThread.start();
        petrovThread.start();
        nechiporchukThread.start();

        ivanovThread.join();
        petrovThread.join();
        nechiporchukThread.join();
    }

    private static void justSleep() {
        try {
            // random sleep time from 100 to 600 ms
            Thread.sleep(random.nextInt(450) + 10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class ProducerConsumerCounter {
        private final BlockingQueue<Integer> fromProducerToConsumer;
        private final BlockingQueue<Integer> fromConsumerToCounter;

        public ProducerConsumerCounter(Integer capacity) {
            this.fromProducerToConsumer = new LinkedBlockingDeque<>(capacity);
            this.fromConsumerToCounter = new LinkedBlockingDeque<>(capacity);
        }

        public void produce(int numItems) throws InterruptedException {
            int curDetail = 1;
            while (true) {
                System.out.println("Producer added item #" + curDetail);
                this.fromProducerToConsumer.put(curDetail++);

                // break if all details were produced
                if (curDetail == numItems + 1)
                    break;

                justSleep();
            }
        }

        public void consume(int numItems) throws InterruptedException {
            while (true) {
                int removed = this.fromProducerToConsumer.take();
                numItems--;
                System.out.println("Consumer removed item #" + removed);
                this.fromConsumerToCounter.put(removed);

                // break if all details were consumed
                if (numItems == 0)
                    break;

                justSleep();
            }
        }

        public void count(int numItems) throws InterruptedException {
            int count = 1;
            while (true) {
                int removed = fromConsumerToCounter.take();
                System.out.println("Counter removed item #" + removed + ", and now counted: " + count++);

                if (count == numItems + 1)
                    break;

                justSleep();
            }
        }
    }
}
