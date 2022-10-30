package Lab_7.xml;

import Lab_7.common.Group;
import Lab_7.common.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class LearningDivision {
    private final String ROOT_TAG = "learning_division";
    private final String GROUP_TAG = "group";
    private final String GROUP_ID_TAG = "id";
    private final String GROUP_NAME_TAG = "name";
    private final String STUDENT_TAG = "student";
    private final String STUDENT_ID_TAG = "id";
    private final String STUDENT_FNAME_TAG = "first_name";
    private final String STUDENT_LNAME_TAG = "last_name";
    private final ArrayList<Group> groups = new ArrayList<>();
    private final ArrayList<Student> students = new ArrayList<>();

    public void saveToFile(String filename) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element root = doc.createElement(ROOT_TAG);
        doc.appendChild(root);

        for (Group group : groups) {
            Element groupElem = doc.createElement(GROUP_TAG);
            groupElem.setAttribute(GROUP_ID_TAG, String.valueOf(group.id));
            groupElem.setAttribute(GROUP_NAME_TAG, group.name);
            root.appendChild(groupElem);

            for (Student student : students) {
                if (Objects.equals(student.group.id, group.id)) {
                    Element studentElem = doc.createElement(STUDENT_TAG);
                    studentElem.setAttribute(STUDENT_ID_TAG, String.valueOf(student.id));
                    studentElem.setAttribute(STUDENT_FNAME_TAG, student.first_name);
                    studentElem.setAttribute(STUDENT_LNAME_TAG, student.last_name);
                    groupElem.appendChild(studentElem);
                }
            }
        }


        try (FileOutputStream output =
                     new FileOutputStream(filename)) {
            writeXml(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

    public void loadFromFile(String filename, String schema) {
        DocumentBuilderFactory dbf;
        DocumentBuilder db;

        int groupID;
        String groupName;
        int studentID;
        String studentFirstname;
        String studentLastname;

        SchemaFactory sf =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s;
        try {
            s = sf.newSchema(new File(schema));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        try {
            dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setSchema(s);
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document doc;

        try {
            doc = db.parse(new File(filename));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        Element root = doc.getDocumentElement();

        if (root.getTagName().equals(ROOT_TAG)) {
            NodeList listGroups = root.getElementsByTagName(GROUP_TAG);
            for (int i = 0; i < listGroups.getLength(); i++) {
                Element group = (Element) listGroups.item(i);
                groupID = Integer.parseInt(group.getAttribute(GROUP_ID_TAG));
                groupName = group.getAttribute(GROUP_NAME_TAG);

                Group group1 = new Group(groupName, groupID);
                groups.add(group1);

                NodeList listStudents = group.getElementsByTagName(STUDENT_TAG);
                for (int j = 0; j < listStudents.getLength(); j++) {
                    Element student = (Element) listStudents.item(j);
                    studentID = Integer.parseInt(student.getAttribute(STUDENT_ID_TAG));
                    studentFirstname = student.getAttribute(STUDENT_FNAME_TAG);
                    studentLastname = student.getAttribute(STUDENT_LNAME_TAG);

                    students.add(new Student(studentID, studentFirstname, studentLastname, group1));
                }
            }
        }
    }

    public void addGroup(int id, String name) {
        boolean idIsPresent = false;

        for (Group group : groups) {
            if (group.id == id) {
                idIsPresent = true;
                break;
            }
        }

        if (idIsPresent) throw new ArithmeticException("Entered ID is already present in list");

        groups.add(new Group(name, id));
    }


    public Group getGroup(int id) {
        for (Group group : groups) {
            if (group.id == id) {
                return group;
            }
        }

        throw new ArithmeticException("No such a group with entered id");
    }

    public Group getGroupInd(int index) {
        try {
            return groups.get(index);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public int countGroups() {
        return groups.size();
    }

    public void deleteGroup(int id) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).id == id) {
                groups.remove(i);
                return;
            }
        }
        throw new ArithmeticException("No such a group with this id");
    }

    public void addStudent(int id, String first_name, String last_name, int classID) {
        for (Student student : students) {
            if (student.id == id) throw new ArithmeticException("Entered ID is already exists");
        }
        for (Group group : groups) {
            if (group.id == classID) {
                students.add(new Student(id, first_name, last_name, group));
                return;
            }
        }
        throw new ArithmeticException("No such a group with entered ID");
    }

    public void print() {
        for (Group group : groups) {
            System.out.println(" - " + group);
            for (Student student : students) {
                if (Objects.equals(student.group.id, group.id))
                    System.out.println(" - - " + student);
            }
        }
        System.out.println();
    }

    static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println(e.getMessage());
        }

        public void error(SAXParseException e) throws SAXException {
            System.out.println(e.getMessage());
        }

        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println(e.getMessage());
        }
    }

}
