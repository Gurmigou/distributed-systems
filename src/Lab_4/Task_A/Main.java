package Lab_4.Task_A;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;

import static Lab_4.Task_A.WriterAndDeleter.OperationType.APPEND;
import static Lab_4.Task_A.WriterAndDeleter.OperationType.DELETE;

public class Main {
    public static final CustomRWLock CUSTOM_RW_LOCK = new CustomRWLock();

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "C:\\Users\\Yehor\\IdeaProjects\\distributed-systems\\src\\Lab_4\\Task_A\\file.txt";
        new PrintWriter(FILE_NAME).close();

        var records = List.of(
                new Record("John", "A", "B", "+1234567890"),
                new Record("Mike", "C", "D","+0987654321"),
                new Record("Alex", "E", "F", "+99999999999"),
                new Record("Peter", "G", "H", "+8888888888"));

        var appender = new WriterAndDeleter(FILE_NAME, records, APPEND);
        var fullNameSearcherByPhone = new FullNameSearcherByPhone(FILE_NAME, "+8888888888");
        var phoneSearcherByLastName = new PhoneSearcherByLastName(FILE_NAME, "F");
        var deleter = new WriterAndDeleter(FILE_NAME, List.of(new Record("Mike", "C", "D","+0987654321")), DELETE);

        appender.start();
        fullNameSearcherByPhone.start();
        phoneSearcherByLastName.start();
        deleter.start();
    }

    public static void justSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
