package Lab_8.task_4.client;

import Lab_7.common.Group;
import Lab_7.common.Student;
import Lab_8.task_4.rmi.RMICommandsInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            RMICommandsInterface rmiCommands = (RMICommandsInterface) registry
                    .lookup("Dao");

            rmiCommands.connectToDataBase();

            String commands =
                    """
                            1. Create new group
                            2. Create new student
                            3. Delete group
                            4. Delete student
                            5. Update group
                            6. Update student
                            7. Get group by id
                            8. Get student by id
                            9. Get all groups
                            10. Get all students with group id
                            11. Exit
                            """;

            Integer operation = null;
            Scanner scanner = new Scanner(System.in);
            System.out.println(commands);

            while (operation == null || operation != 11) {
                System.out.println("Input operation:");
                operation = Integer.parseInt(scanner.next());

                try {
                    switch (operation) {
                        case 1 -> {
                            Group group = getGroupInput(false, true);
                            rmiCommands.createNewGroup(group);
                            System.out.println("Operation completed successfully");
                        }
                        case 2 -> {
                            Student student = getStudentInput(false, true, true, false);
                            rmiCommands.createNewStudent(student);
                            System.out.println("Operation completed successfully");
                        }
                        case 3 -> {
                            int numberInput = getNumberInput("Input group id: ");
                            rmiCommands.deleteGroup(numberInput);
                            System.out.println("Operation completed successfully");
                        }
                        case 4 -> {
                            int numberInput = getNumberInput("Input student id: ");
                            rmiCommands.deleteStudent(numberInput);
                            System.out.println("Operation completed successfully");
                        }
                        case 5 -> {
                            Group group = getGroupInput(true, true);
                            rmiCommands.updateGroup(group);
                            System.out.println("Operation completed successfully");
                        }
                        case 6 -> {
                            Student student = getStudentInput(true, true, true, false);
                            rmiCommands.updateStudent(student);
                            System.out.println("Operation completed successfully");
                        }
                        case 7 -> {
                            int numberInput = getNumberInput("Input group id: ");
                            System.out.println(rmiCommands.findGroupById(numberInput));
                            System.out.println("Operation completed successfully");
                        }
                        case 8 -> {
                            int numberInput = getNumberInput("Input student id: ");
                            System.out.println(rmiCommands.findStudentById(numberInput));
                            System.out.println("Operation completed successfully");
                        }
                        case 9 -> {
                            System.out.println(rmiCommands.findAllGroups());
                            System.out.println("Operation completed successfully");
                        }
                        case 10 -> {
                            int numberInput = getNumberInput("Input group id: ");
                            System.out.println(rmiCommands.findAllStudentsWithGroupId(numberInput));
                            System.out.println("Operation completed successfully");
                        }
                        case 11 -> System.out.println("Program stopped");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            rmiCommands.closeConnection();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getNumberInput(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }

    public static String getStringInput(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Group getGroupInput(boolean withId, boolean withName) {
        String name = null;
        if (withName) {
            name = getStringInput("Input group name: ");
        }
        Integer id = null;
        if (withId) {
            id = getNumberInput("Input group id: ");
        }
        return new Group(name, id);
    }

    public static Student getStudentInput(boolean withId, boolean withGroup, boolean withGroupId, boolean withGroupName) {
        String first_name = getStringInput("Enter student first name: ");
        String last_name = getStringInput("Enter student last name: ");
        int id = 0;
        if (withId) {
            id = getNumberInput("Enter student id: ");
        }
        if (withGroup)
            return new Student(id, first_name, last_name, getGroupInput(withGroupId, withGroupName));
        else
            return new Student(id, first_name, last_name, null);
    }
}
