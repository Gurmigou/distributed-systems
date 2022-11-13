package Lab_7.common;

import java.io.Serializable;

public class Student implements Serializable {
    public int id;
    public String first_name;
    public String last_name;
    public Group group;

    public Student(int id, String first_name, String last_name, Group group) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", group=" + group +
                '}';
    }
}