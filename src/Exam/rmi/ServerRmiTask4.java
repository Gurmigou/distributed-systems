package Exam.rmi;

import Exam.model.Abiturient;
import Exam.socket.DataObject;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRmiTask4 {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1000);
        RMIServerTask4 service = new Service();
        registry.rebind("exam", service);

        System.out.println("Server started!");
    }

    static class Service extends UnicastRemoteObject implements RMIServerTask4 {
        Service() throws RemoteException {
        }

        @Override
        public List<Abiturient> displayBadMarks() throws RemoteException {
            return DataObject.getAbiturientsWithGradeLessMin();
        }

        @Override
        public List<Abiturient> displaySumBiggerThan(int min) throws RemoteException {
            return DataObject.getAbiturientsWithGradeHigherThan(min);
        }

        @Override
        public List<Abiturient> displayNHighestSum(int n) throws RemoteException {
            List<Abiturient> result = new ArrayList<>();
            List<Abiturient> high = DataObject.getAbiturientsWithGradeMoreHigh()
                    .stream()
                    .sorted()
                    .limit(n)
                    .toList();
            List<Abiturient> norm = DataObject.getAbiturientsWithGradeMoreNorm();
            result.addAll(high);
            result.addAll(norm);
            return result;
        }
    }
}
