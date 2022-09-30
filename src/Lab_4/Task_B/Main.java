package Lab_4.Task_B;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    private static final int N = 5;
    private static final int M = 10;
    public static final String FILE_LOG_NAME = "C:\\Users\\Yehor\\IdeaProjects\\distributed-systems\\src\\Lab_4\\Task_B\\garden_log.txt";
    public static final ReentrantReadWriteLock RWLock = new ReentrantReadWriteLock();

    public enum PlantState {
        WATERED,
        DRY;

        public String toString() {
            return this == WATERED ? "W" : "D";
        }
    }

    public static void main(String[] args) {
        PlantState[][] garden = createGarden();
        ConsoleLoggerThread consoleLoggerThread = new ConsoleLoggerThread(garden);
        FileLoggerThread fileLoggerThread = new FileLoggerThread(garden);
        GardenerThread gardenerThread = new GardenerThread(garden);
        NatureThread natureThread = new NatureThread(garden);

        consoleLoggerThread.start();
        fileLoggerThread.start();
        gardenerThread.start();
        natureThread.start();
    }

    public static void justSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static PlantState[][] createGarden() {
        PlantState[][] garden = new PlantState[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                garden[i][j] = PlantState.WATERED;
            }
        }
        return garden;
    }
}
