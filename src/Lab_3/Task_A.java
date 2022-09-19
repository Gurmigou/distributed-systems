package Lab_3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Task_A {
    private static final Integer N = 10;
    private static final Semaphore BEE_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore BEAR_SEMAPHORE = new Semaphore(1);
    private static final List<Integer> POT = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> beeThreads = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            ConsumerProducer bee = new ConsumerProducer();
            beeThreads.add(new Thread(() -> {
                while (true) {
                    try {
                        bee.produce();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }

        ConsumerProducer bear = new ConsumerProducer();
        Thread bearThread = new Thread(() -> {
            while (true) {
                try {
                    bear.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        beeThreads.forEach(Thread::start);
        BEAR_SEMAPHORE.acquire();
        bearThread.start();

        bearThread.join();
    }

    private static void justSleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ConsumerProducer {
        private final Integer index;
        private static Integer nextIndex = 1;

        {
            index = nextIndex++;
        }

        public void produce() throws InterruptedException {
            while (!Thread.interrupted()) {
                BEE_SEMAPHORE.acquire();

                System.out.println("Bee #" + index + " produced honey: " + POT.size());
                POT.add(1);
                justSleep();

                if (POT.size() == N) {
                    BEAR_SEMAPHORE.release();
                } else {
                    BEE_SEMAPHORE.release();
                }
            }
        }

        public void consume() throws InterruptedException {
            while (!Thread.interrupted()) {
                BEAR_SEMAPHORE.acquire();

                System.out.println("Bear consumed all honey");
                POT.clear();

                BEE_SEMAPHORE.release();
            }
        }
    }
}
