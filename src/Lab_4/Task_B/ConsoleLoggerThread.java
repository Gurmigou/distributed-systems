package Lab_4.Task_B;

import Lab_4.Task_B.Main.PlantState;

import static Lab_4.Task_B.Main.RWLock;
import static Lab_4.Task_B.Main.justSleep;

public class ConsoleLoggerThread extends Thread {
    private final PlantState[][] garden;

    public ConsoleLoggerThread(PlantState[][] garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            RWLock.readLock().lock();
            StringBuilder sb = new StringBuilder();
            for (PlantState[] plantStates : garden) {
                for (PlantState plantState : plantStates) {
                    sb.append(plantState.toString()).append(' ');
                }
                sb.append("\n");
            }
            System.out.println(sb);
            RWLock.readLock().unlock();

            justSleep(4500);
        }
    }
}
