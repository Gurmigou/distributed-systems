package Lab_4.Task_B;

import Lab_4.Task_B.Main.PlantState;

import static Lab_4.Task_B.Main.RWLock;
import static Lab_4.Task_B.Main.justSleep;

public class GardenerThread extends Thread {
    private final PlantState[][] garden;

    public GardenerThread(PlantState[][] garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            RWLock.writeLock().lock();
            for (int i = 0; i < garden.length; i++) {
                for (int j = 0; j < garden[i].length; j++) {
                    if (garden[i][j] == PlantState.DRY) {
                        garden[i][j] = PlantState.WATERED;
                    }
                }
            }
            RWLock.writeLock().unlock();

            justSleep(2000);
        }
    }
}
