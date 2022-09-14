package Lab_2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task_A {
    private static final AtomicBoolean WINNIE_FOUND = new AtomicBoolean(false);
    private static final AtomicInteger SEARCHING_IN_FOREST_FOW = new AtomicInteger(0);
    private static final Random random = new Random();

    // Forest has square form
    private final Integer forestSize;
    private final Integer beesNum;
    private final boolean[][] forest;

    public Task_A(Integer forestSize, Integer beesNum) {
        this.forestSize = forestSize;
        this.beesNum = beesNum;
        this.forest = createForestAndSetWinnie();
    }

    public static void main(String[] args) {
        Task_A taskA = new Task_A(10, 5);
        taskA.startSearch();
    }

    private void startSearch() {
        BeesThread[] beesThreads = new BeesThread[beesNum];
        for (int i = 0; i < beesNum; i++) {
            beesThreads[i] = new BeesThread(forest);
            beesThreads[i].start();
        }

        // make current thread wait until all bees threads finish
        for (int i = 0; i < beesNum; i++) {
            try {
                beesThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean[][] createForestAndSetWinnie() {
        boolean[][] forest = new boolean[forestSize][forestSize];

        int winnieRow = random.nextInt(forestSize);
        int winnieCol = random.nextInt(forestSize);
        System.out.println("Winnie is in " + winnieRow + " " + winnieCol);

        forest[winnieRow][winnieCol] = true;
        return forest;
    }

    private static void searchTimeSleep() {
        int sleepTime = random.nextInt(200);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class BeesThread extends Thread {
        private final boolean[][] forest;
        private final int forestSize;
        private final int beesIndex;

        private static int nextIndex = 0;

        public BeesThread(boolean[][] forest) {
            this.forest = forest;
            this.forestSize = forest.length;
            this.beesIndex = nextIndex;
            nextIndex++;
        }

        @Override
        public void run() {
            while (!WINNIE_FOUND.get()) {
                int currentRow = SEARCHING_IN_FOREST_FOW.incrementAndGet();
                if (currentRow < forestSize) {
                    if (winnieFoundInRow(currentRow)) {
                        WINNIE_FOUND.set(true);
                        System.out.println("Bees group #" + beesIndex + " found Winnie in row " + currentRow);
                    }
                } else {
                    break;
                }
            }
        }

        private boolean winnieFoundInRow(int row) {
            for (int col = 0; col < forestSize; col++) {
                if (!WINNIE_FOUND.get()) {
                    searchTimeSleep();
                    if (forest[row][col])
                        return true;
                }
            }
            return false;
        }
    }
}