package Lab_4.Task_B;

import Lab_4.Task_B.Main.PlantState;

import static Lab_4.Task_B.Main.RWLock;
import static Lab_4.Task_B.Main.justSleep;

public class NatureThread extends Thread {
    private final PlantState[][] garden;

    public NatureThread(PlantState[][] garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            RWLock.writeLock().lock();
            // Dry 3 plants
            for (int i = 0; i < 3; i++) {
                garden[getRandomRow()][getRandomColumn()] = PlantState.DRY;
            }

            RWLock.writeLock().unlock();
            justSleep(450);
        }
    }

    private int getRandomRow() {
        return (int) (Math.random() * garden.length);
    }

    private int getRandomColumn() {
        return (int) (Math.random() * garden[0].length);
    }
}
