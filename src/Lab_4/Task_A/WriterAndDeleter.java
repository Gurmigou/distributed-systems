package Lab_4.Task_A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static Lab_4.Task_A.Main.CUSTOM_RW_LOCK;
import static Lab_4.Task_A.Main.justSleep;
import static Lab_4.Task_A.WriterAndDeleter.OperationType.DELETE;

public class WriterAndDeleter extends Thread {
    public enum OperationType {
        APPEND,
        DELETE
    }

    private final String FILE_NAME;
    private final OperationType type;
    private final List<Record> records;

    public WriterAndDeleter(String fileName, List<Record> records, OperationType type) {
        this.FILE_NAME = fileName;
        this.records = records;
        this.type = type;
    }

    @Override
    public void run() {
        for (var record : records) {
            Path path = Path.of(FILE_NAME);
            String recordString = record.toString();

            if (type == DELETE) {
                while (true) {
                    CUSTOM_RW_LOCK.writeLock();
                    try {
                        List<String> lines = Files.readAllLines(path);
                        boolean removed = lines.remove(recordString.substring(0, recordString.length() - 1));

                        if (removed) {
                            Files.write(path, lines);
                            System.out.print("Removed record: " + recordString);
                            CUSTOM_RW_LOCK.writeUnlock();
                            break;
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    CUSTOM_RW_LOCK.writeUnlock();
                    justSleep(1500);
                }
            } else {
                CUSTOM_RW_LOCK.writeLock();
                try {
                    Files.write(path, recordString.getBytes(), StandardOpenOption.APPEND);
                    System.out.print("Appended record: " + recordString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                CUSTOM_RW_LOCK.writeUnlock();
                justSleep(1500);
            }
        }
    }
}
