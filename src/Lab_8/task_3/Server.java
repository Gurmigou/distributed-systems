package Lab_8.task_3;

import Lab_7.common.Group;
import Lab_7.common.Student;
import Lab_7.jdbc.DataAccessObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static ObjectInputStream in;
    static ObjectOutputStream out;
    static Socket socket;
    static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(2001);
            socket = serverSocket.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataAccessObject dao = new DataAccessObject();

        int operation = 0;
        do {
            try {
                operation = (Integer) in.readObject();
                switch (operation) {
                    case 1 -> {
                        String name;
                        int id;
                        sendMessage("Enter group name: ");
                        name = (String) in.readObject();
                        sendMessage("Enter group id: ");
                        id = Integer.parseInt((String) in.readObject());
                        Group group = new Group(name, id);
                        dao.createNewGroup(group);
                        System.out.println("Operation completed successfully");
                    }
                    case 2 -> {
                        String fname, lname, groupname;
                        Integer id;
                        sendMessage("Enter student id: ");
                        id = Integer.parseInt((String) in.readObject());

                        sendMessage("Enter first name: ");
                        fname = (String) in.readObject();

                        sendMessage("Enter last name: ");
                        lname = (String) in.readObject();

                        sendMessage("Enter group name: ");
                        groupname = (String) in.readObject();

                        sendMessage("Enter student id: ");
                        id = Integer.parseInt((String) in.readObject());

                        Group group = new Group(groupname, id);
                        Student student = new Student(id, fname, lname, group);
                        dao.createNewStudent(student);
                        System.out.println("Operation completed successfully");
                    }
                    case 3 -> {
                        sendMessage("Enter group id: ");
                        int id = Integer.parseInt((String) in.readObject());
                        dao.deleteGroup(id);
                        System.out.println("Operation completed successfully");
                    }
                    case 4 -> {
                        sendMessage("Enter student id: ");
                        int id = Integer.parseInt((String) in.readObject());
                        dao.deleteStudent(id);
                        System.out.println("Operation completed successfully");
                    }
                    case 5 -> {
                        sendMessage("Enter group name: ");
                        String groupname = (String) in.readObject();

                        sendMessage("Enter student id: ");
                        int id = Integer.parseInt((String) in.readObject());

                        Group group = new Group(groupname, id);
                        dao.updateGroup(group);
                        System.out.println("Operation completed successfully");
                    }
                    case 6 -> {
                        String fname, lname, groupname;
                        Integer id;
                        sendMessage("Enter student id: ");
                        id = Integer.parseInt((String) in.readObject());

                        sendMessage("Enter first name: ");
                        fname = (String) in.readObject();

                        sendMessage("Enter last name: ");
                        lname = (String) in.readObject();

                        sendMessage("Enter group name: ");
                        groupname = (String) in.readObject();

                        Group group = new Group(groupname, id);

                        Student student = new Student(id, fname, lname, group);
                        dao.updateStudent(student);
                        System.out.println("Operation completed successfully");
                    }
                    case 7 -> {
                        sendMessage("Enter group id: ");
                        int id = Integer.parseInt((String) in.readObject());
                        sendMessage("print");
                        sendMessage(dao.findGroupById(id).get());
                        //System.out.println(dao.findGroupById(numberInput));
                        System.out.println("Operation completed successfully");
                    }
                    case 8 -> {
                        sendMessage("Enter student id: ");
                        int id = Integer.parseInt((String) in.readObject());
                        sendMessage("print");
                        sendMessage(dao.findStudentById(id).get());

                        //int numberInput = getNumberInput("Input student id: ");
                        //System.out.println(dao.findStudentById(numberInput));
                        System.out.println("Operation completed successfully");
                    }
                    case 9 -> {
                        sendMessage("print");
                        sendMessage(dao.findAllGroups());
                        System.out.println("Operation completed successfully");
                    }
                    case 10 -> {
                        sendMessage("Enter group id: ");
                        int id = Integer.parseInt((String) in.readObject());
                        //int numberInput = getNumberInput("Input group id: ");
                        sendMessage("print");
                        sendMessage(dao.findAllStudentsWithGroupId(id));
                        System.out.println("Operation completed successfully");
                    }
                    case 11 -> {
                        System.out.println("Program stopped");
                        dao.closeConnection();
                    }
                }
                sendMessage("stop");
                System.out.println("end of iter");
            } catch (Exception e) {
                System.out.println("Happened error: " + e.getMessage());
            }
        } while (operation != 11);
    }

    private static boolean readMessage(String msg) {
        if (msg == null || msg == "")
            return false;

        return true;
    }

    public static void sendMessage(Object msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
