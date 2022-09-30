package Lab_4.Task_A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static Lab_4.Task_A.Main.CUSTOM_RW_LOCK;
import static Lab_4.Task_A.Main.justSleep;

public class PhoneSearcherByLastName extends Thread {
    private final String FILE_NAME;
    private final String lastName;

    public PhoneSearcherByLastName(String fileName, String lastName) {
        this.FILE_NAME = fileName;
        this.lastName = lastName;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            CUSTOM_RW_LOCK.readLock();
            Path path = Path.of(FILE_NAME);

            try {
                var lines = Files.readAllLines(path);
                for (var line : lines) {
                    Record record = Record.fromString(line);
                    if (record.getLastName().equals(lastName)) {
                        System.out.println("Found phone number: " + record.getPhoneNumber() + " by last name: " + record.getLastName());
                        CUSTOM_RW_LOCK.readUnlock();
                        return;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            CUSTOM_RW_LOCK.readUnlock();
            justSleep(150);
        }
    }
}
