package Lab_7.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LearningDivision learningDivision = new LearningDivision();
        learningDivision.loadFromFile(
                "C:\\Users\\Yehor\\IdeaProjects\\distributed-systems\\src\\Lab_7\\resources\\learning_division.xml",
                "C:\\Users\\Yehor\\IdeaProjects\\distributed-systems\\src\\Lab_7\\resources\\learning_division.xsd");
        learningDivision.print();

        String operation = "0";

        while (!operation.equals("9")) {
            System.out.println("\t1. Add group;");
            System.out.println("\t2. Get group by id;");
            System.out.println("\t3. Get group by index;");
            System.out.println("\t4. Get number of groups;");
            System.out.println("\t5. Delete group by id;");
            System.out.println("\t6. Add student;");
            System.out.println("\t7. Print all;");
            System.out.println("\t8. Save all;");
            System.out.println("\t9. Exit;");

            System.out.print("\nChoose your operation: ");

            Scanner scanner = new Scanner(System.in);
            operation = scanner.nextLine();

            String groupName;
            int group_id;
            int index;
            String studentFirstName;
            String studentLastName;
            int student_id;
            switch (Integer.parseInt(operation)) {
                case 1:
                    System.out.print("Enter group name: ");
                    groupName = scanner.nextLine();
                    System.out.print("Enter group id: ");
                    group_id = Integer.parseInt(scanner.nextLine());
                    learningDivision.addGroup(group_id, groupName);
                    break;
                case 2:
                    System.out.print("Enter group id: ");
                    group_id = Integer.parseInt(scanner.nextLine());
                    System.out.println(learningDivision.getGroup(group_id));
                    break;
                case 3:
                    System.out.print("Enter group index: ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.println(learningDivision.getGroupInd(index));
                    break;
                case 4:
                    System.out.println(learningDivision.countGroups());
                    break;
                case 5:
                    System.out.print("Enter group id: ");
                    group_id = Integer.parseInt(scanner.nextLine());
                    learningDivision.deleteGroup(group_id);
                    break;
                case 6:
                    System.out.print("Enter student first name: ");
                    studentFirstName = scanner.nextLine();
                    System.out.print("Enter student last name: ");
                    studentLastName = scanner.nextLine();
                    System.out.print("Enter student id: ");
                    student_id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter group id: ");
                    group_id = Integer.parseInt(scanner.nextLine());
                    learningDivision.addStudent(student_id, studentFirstName, studentLastName, group_id);
                    break;
                case 7:
                    learningDivision.print();
                    break;
                case 8:
                    try {
                        learningDivision.saveToFile("C:\\Users\\Yehor\\IdeaProjects\\distributed-systems\\src\\Lab_7\\resources\\learning_division.xml");
                    } catch (ParserConfigurationException | TransformerException e) {
                        throw new RuntimeException(e);
                    }
                default:
                    break;
            }

            System.out.println();
        }
    }
}
