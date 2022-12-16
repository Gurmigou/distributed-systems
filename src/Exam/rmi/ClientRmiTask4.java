package Exam.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRmiTask4 {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Scanner in = new Scanner(System.in);
        try {
            RMIServerTask4 server = (RMIServerTask4) Naming.lookup("//localhost:1000/exam");

            while (true) {
                System.out.println("""
                    Choose option:
                    1 - display Abiturients with bad marks
                    2 - display Abiturients with sum marks higher than given N
                    3 - display N Abiturients with the highest marks sum
                    4 - exit
                    """);

                int command = in.nextInt();

                if (command == 4) {
                    System.out.println("Client stopped");
                    break;
                }

                switch (command) {
                    case 1 -> System.out.println(server.displayBadMarks());
                    case 2 -> {
                        System.out.println("Enter N:");
                        int n = in.nextInt();
                        System.out.println(server.displaySumBiggerThan(n));
                    }
                    case 3 -> {
                        System.out.println("Enter N:");
                        int n = in.nextInt();
                        System.out.println(server.displayNHighestSum(n));
                    }
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
