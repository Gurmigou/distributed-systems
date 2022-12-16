package Exam.socket;

import Exam.model.Abiturient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientSocketTask4 {
    private static final Integer PORT = ServerSocketTask4.PORT;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;

        PrintWriter out;
        ObjectInputStream in = null;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    Choose option:
                    1 - display Abiturients with bad marks
                    2 - display Abiturients with sum marks higher than given N
                    3 - display N Abiturients with the highest marks sum
                    4 - exit
                    """);

            socket = new Socket(host.getHostName(), PORT);
            out = new PrintWriter(socket.getOutputStream());

            int commandIndex = scanner.nextInt();

            switch (commandIndex) {
                case 1 -> {
                    String message = "1";
                    out.println(message);
                    out.flush();
                }
                case 2, 3 -> {
                    System.out.println("Enter N");
                    int count = scanner.nextInt();
                    String message = commandIndex + "#" + count;
                    out.println(message);
                    out.flush();
                }
                case 4 -> {
                    String message = "4";
                    out.println(message);
                    out.flush();
                }
            }

            if (commandIndex == 4) {
                System.out.println("Client stopped");
                break;
            }

            System.out.println("Results: ");
            in = new ObjectInputStream(socket.getInputStream());

            List<Abiturient> results = (List<Abiturient>) in.readObject();
            results.forEach(System.out::println);
        }

        if (in != null)
            in.close();

        out.close();
        System.out.println("Shutting down client!!");
    }
}
