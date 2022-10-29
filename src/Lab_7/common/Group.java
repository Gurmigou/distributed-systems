package Lab_7.common;

public class Group {
    public String name;
    public Integer id;

    public Group(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


