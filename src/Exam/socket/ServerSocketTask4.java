package Exam.socket;

import Exam.model.Abiturient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSocketTask4 {
    public static final Integer PORT = 9000;
    private static final AtomicBoolean shouldEnd = new AtomicBoolean(false);

    public static void main(String[] args) {
        System.out.println("Server started");
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            server.setReuseAddress(true);

            while (!shouldEnd.get()) {
                Socket client = server.accept();
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            ObjectOutputStream out = null;
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                String message = in.readLine();
                String[] splitMessage = message.split("#");

                String commandIndex = splitMessage[0];
                System.out.println("Received input: command " + splitMessage[0]);

                if (commandIndex.equals("4")) {
                    System.out.println("Closing server");
                    shouldEnd.getAndSet(true);
                    return;
                }

                List<Abiturient> result = null;
                switch (commandIndex) {
                    case "1" -> result = DataObject.getAbiturientsWithGradeLessMin();
                    case "2" -> {
                        int min = Integer.parseInt(splitMessage[1]);
                        result = DataObject.getAbiturientsWithGradeHigherThan(min);
                    }
                    case "3" -> {
                        int n = Integer.parseInt(splitMessage[1]);
                        List<Abiturient> high = DataObject.getAbiturientsWithGradeMoreHigh()
                                .stream()
                                .sorted()
                                .limit(n)
                                .toList();
                        List<Abiturient> norm = DataObject.getAbiturientsWithGradeMoreNorm();
                        result = new ArrayList<>();
                        result.addAll(high);
                        result.addAll(norm);
                    }
                }

                out.writeObject(result);
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}