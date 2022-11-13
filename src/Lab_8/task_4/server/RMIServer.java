package Lab_8.task_4.server;

import Lab_8.task_4.rmi.RMICommands;
import Lab_8.task_4.rmi.RMICommandsInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static void main(String[] args) {
        try {
            RMICommandsInterface remote = new RMICommands();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Dao", remote);

        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
