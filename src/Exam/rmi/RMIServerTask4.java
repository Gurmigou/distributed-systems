package Exam.rmi;

import Exam.model.Abiturient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServerTask4 extends Remote {
    List<Abiturient> displayBadMarks() throws RemoteException;

    List<Abiturient> displaySumBiggerThan(int n) throws RemoteException;

    List<Abiturient> displayNHighestSum(int n) throws RemoteException;
}
