package Lab_8.task_5;

import Lab_7.common.Group;
import Lab_7.common.Student;
import Lab_7.jdbc.DataAccessObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static Lab_8.task_5.RabbitMqUtil.deserializeObject;

public class ConsumerServer {
    private static final DataAccessObject dao = new DataAccessObject();

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            Connection rabbitmqConnection = factory.newConnection();
            Channel channel = rabbitmqConnection.createChannel();

            // create a queue on a rabbit mq server
            channel.queueDeclare("lab-8", false, false, false, null);

            channel.basicConsume("lab-8", true, (consumerTag, delivery) -> handleRequest(delivery.getBody()), consumerTag -> {});

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleRequest(byte[] data) {
        try {
            DataObject dataObject = deserializeObject(data);

            switch (dataObject.getCommand()) {
                case 1 -> {
                    Group group = (Group) dataObject.getData();
                    dao.createNewGroup(group);
                }
                case 2 -> {
                    Student student = (Student) dataObject.getData();
                    dao.createNewStudent(student);
                }
                case 3 -> {
                    int numberInput = (int) dataObject.getData();
                    dao.deleteGroup(numberInput);
                }
                case 4 -> {
                    int numberInput = (int) dataObject.getData();
                    dao.deleteStudent(numberInput);
                }
                case 5 -> {
                    Group group = (Group) dataObject.getData();
                    dao.updateGroup(group);
                }
                case 6 -> {
                    Student student = (Student) dataObject.getData();
                    dao.updateStudent(student);
                }
                case 7 -> {
                    int numberInput = (int) dataObject.getData();
                    System.out.println(dao.findGroupById(numberInput));
                }
                case 8 -> {
                    int numberInput = (int) dataObject.getData();
                    System.out.println(dao.findStudentById(numberInput));
                }
                case 9 -> {
                    System.out.println(dao.findAllGroups());
                }
                case 10 -> {
                    int numberInput = (int) dataObject.getData();
                    System.out.println(dao.findAllStudentsWithGroupId(numberInput));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}































