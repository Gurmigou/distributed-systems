package Lab_8.task_4.rmi;

import Lab_7.common.Group;
import Lab_7.common.Student;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMICommandsInterface extends Remote {

    void createNewGroup(Group group) throws RemoteException;

    void createNewStudent(Student student) throws RemoteException;

    void deleteGroup(Integer id) throws RemoteException;

    void deleteStudent(Integer id) throws RemoteException;

    void updateGroup(Group group) throws RemoteException;

    void updateStudent(Student student) throws RemoteException;

    Group findGroupById(Integer id) throws RemoteException;

    Student findStudentById(Integer id) throws RemoteException;

    List<Student> findAllStudentsWithGroupId(Integer id) throws RemoteException;

    List<Group> findAllGroups() throws RemoteException;

    void connectToDataBase() throws RemoteException;

    void closeConnection() throws RemoteException;
}