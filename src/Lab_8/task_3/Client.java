package Lab_8.task_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static ObjectInputStream in;
    static ObjectOutputStream out;
    static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        socket = new Socket("localhost", 2001);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

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

        Integer operation = null;
        Scanner scanner = new Scanner(System.in);

        while (operation == null || operation != 11) {
            System.out.println("Input operation:");
            operation = Integer.parseInt(scanner.next());
            sendMessage(operation);

            Object obj;
            do {
                obj = in.readObject();
                if (!obj.equals("stop")) {
                    System.out.println(obj);
                    if (obj.equals("print")) {
                        Object list = in.readObject();
                        System.out.println(list);
                    }
                    else {
                        String response = scanner.next();
                        sendMessage(response);
                    }

                }
            } while (!obj.equals("stop"));
        }
    }

    public static void sendMessage(Object msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean readMessage(Object msg) {
        if (msg == null)
            return false;

        System.out.println(msg);
        return true;
    }


}
