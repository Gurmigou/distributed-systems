package Lab_4.Task_A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Lab_4.Task_A.Main.CUSTOM_RW_LOCK;
import static Lab_4.Task_A.Main.justSleep;

public class FullNameSearcherByPhone extends Thread {
    private final String FILE_NAME;
    private final String phone;

    public FullNameSearcherByPhone(String fileName, String phone) {
        this.FILE_NAME = fileName;
        this.phone = phone;
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
                    if (record.getPhoneNumber().equals(phone)) {
                        System.out.println("Found full name: " + record.getFullName() + " by phone: " + record.getPhoneNumber());
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











