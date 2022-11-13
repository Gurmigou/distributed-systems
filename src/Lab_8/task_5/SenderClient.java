package Lab_8.task_5;

import Lab_7.common.Group;
import Lab_7.common.Student;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static Lab_8.task_5.RabbitMqUtil.serializeObject;

public class SenderClient {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection rabbitmqConnection = factory.newConnection()) {
            Channel channel = rabbitmqConnection.createChannel();

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

            System.out.println(commands);

            // create a queue on a rabbit mq server
            channel.queueDeclare("lab-8", false, false, false, null);

            while (true) {
                DataObject dataObjectInput = getDataObjectInput();
                if (dataObjectInput == null || dataObjectInput.getCommand() == 11) {
                    break;
                }

                channel.basicPublish("", "lab-8", null, serializeObject(dataObjectInput));
                System.out.println("Message has been sent");
            }

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataObject getDataObjectInput() {


        Integer operation = null;
        Scanner scanner = new Scanner(System.in);

        while (operation == null || operation != 11) {
            System.out.println("Input operation:");
            operation = Integer.parseInt(scanner.next());

            try {
                switch (operation) {
                    case 1 -> {
                        Group group = getGroupInput(false, true);
                        return new DataObject(operation, group);
                    }
                    case 2 -> {
                        Student student = getStudentInput(false, true, true, false);
                        return new DataObject(operation, student);
                    }
                    case 3, 7, 10 -> {
                        int numberInput = getNumberInput("Input group id: ");
                        return new DataObject(operation, numberInput);
                    }
                    case 4, 8 -> {
                        int numberInput = getNumberInput("Input student id: ");
                        return new DataObject(operation, numberInput);
                    }
                    case 5 -> {
                        Group group = getGroupInput(true, true);
                        return new DataObject(operation, group);
                    }
                    case 6 -> {
                        Student student = getStudentInput(true, true, true, false);
                        return new DataObject(operation, student);
                    }
                    case 9 -> {
                        return new DataObject(operation, null);
                    }
                    case 11 -> {
                        System.out.println("Program stopped");
                        return new DataObject(operation, null);
                    }
                }
            } catch (Exception e) {
                System.out.println("Happened error: " + e.getMessage());
            }
        }
        return null;
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
        String firstName = getStringInput("Enter student first name: ");
        String lastName = getStringInput("Enter student last name: ");
        int id = 0;
        if (withId) {
            id = getNumberInput("Enter student id: ");
        }
        if (withGroup)
            return new Student(id, firstName, lastName, getGroupInput(withGroupId, withGroupName));
        else
            return new Student(id, firstName, lastName, null);
    }
}
