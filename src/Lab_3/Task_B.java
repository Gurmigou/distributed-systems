package Lab_3;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class Task_B {
    private static final LinkedBlockingDeque<Integer> VISITORS = new LinkedBlockingDeque<>();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread hairdresser = new Thread(() -> {
            try {
                new ProducerConsumer().consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread[] visitorsArr = new Thread[3];
        for (int i = 0; i < 3; i++) {
            visitorsArr[i] = new Thread(() -> new ProducerConsumer().produce());
        }

        hairdresser.start();
        Arrays.stream(visitorsArr).forEach(Thread::start);

        hairdresser.join();
    }

    @SuppressWarnings("BusyWait")
    static class ProducerConsumer {
        private final Integer index;
        private static Integer nextIndex = 1;

        {
            index = nextIndex++;
        }

        public void produce() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(350 + RANDOM.nextInt(900));

                    System.out.println("Visitor " + index + " is waiting");
                    VISITORS.add(index);

                    Thread.sleep(1500 + RANDOM.nextInt(5000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (!Thread.interrupted()) {
                if (VISITORS.isEmpty())
                    System.out.println("Hairdresser is sleeping");
                Integer visitor = VISITORS.take();
                System.out.println("Hairdresser is cutting hair of visitor " + visitor);
            }
        }
    }
}
