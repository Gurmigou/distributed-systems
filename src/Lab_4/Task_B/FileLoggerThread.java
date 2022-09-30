package Lab_4.Task_B;

import Lab_4.Task_B.Main.PlantState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static Lab_4.Task_B.Main.*;

public class FileLoggerThread extends Thread {
    private final PlantState[][] garden;

    public FileLoggerThread(PlantState[][] garden) {
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
            sb.append("\n");
            try {
                Files.write(Path.of(FILE_LOG_NAME), sb.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
                RWLock.readLock().unlock();
                throw new RuntimeException(e);
            }
            RWLock.readLock().unlock();

            justSleep(5000);
        }
    }
}
