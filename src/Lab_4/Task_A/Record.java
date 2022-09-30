package Lab_4.Task_A;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Record {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;

    public String toString() {
        return String.format("%s %s %s %s\n", firstName, middleName, lastName, phoneNumber);
    }

    public static Record fromString(String str) {
        String[] parts = str.split(" ");
        return new Record(parts[0], parts[1], parts[2], parts[3]);
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, middleName, lastName);
    }
}
