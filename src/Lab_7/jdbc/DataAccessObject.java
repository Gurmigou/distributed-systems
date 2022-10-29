package Lab_7.jdbc;

import Lab_7.common.Group;
import Lab_7.common.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataAccessObject {
    private static final String URL = "jdbc:mysql://localhost:3306/lab_7";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private final Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath", e);
        }
    }

    public DataAccessObject() {
        this.connection = connectToDatabase();
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection connectToDatabase() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewGroup(Group group) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `group` (name) VALUES (?)");
            ps.setString(1, group.name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewStudent(Student student) {
        if (student.group == null) {
            throw new IllegalArgumentException("Student must have a group");
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO student (first_name, last_name, group_id) VALUES (?, ?, ?)");
            ps.setString(1, student.first_name);
            ps.setString(2, student.last_name);
            ps.setInt(3, student.group.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGroup(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `group` WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGroup(Group group) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE `group` SET name = ? WHERE id = ?");
            ps.setString(1, group.name);
            ps.setInt(2, group.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(Student student) {
        if (student.group == null) {
            throw new IllegalArgumentException("Student must have a group");
        }

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE student SET first_name = ?, last_name = ?, group_id = ? WHERE id = ?");
            ps.setString(1, student.first_name);
            ps.setString(2, student.last_name);
            ps.setInt(3, student.group.id);
            ps.setInt(4, student.id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Group> findGroupById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `group` WHERE id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
                return Optional.of(new Group(resultSet.getString("name"), resultSet.getInt("id")));
            else
                return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Student> findStudentById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
                return Optional.of(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        findGroupById(resultSet.getInt("group_id")).get())
                );
            else
                return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Group> findAllGroups() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `group`");
            ResultSet resultSet = ps.executeQuery();

            List<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getString("name"), resultSet.getInt("id")));
            }
            return groups;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findAllStudentsWithGroupId(int groupId) {
        try {
            Optional<Group> groupOptional = findGroupById(groupId);
            if (groupOptional.isEmpty())
                return new ArrayList<>();

            Group group = groupOptional.get();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student WHERE group_id = ?");
            ps.setInt(1, groupId);
            ResultSet resultSet = ps.executeQuery();

            List<Student> student = new ArrayList<>();
            while (resultSet.next()) {
                student.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        group)
                );
            }
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
