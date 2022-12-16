package Exam.model;

import java.io.Serializable;
import java.util.Arrays;

public class Abiturient implements Serializable, Comparable<Abiturient> {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private int[] marks;

    public Abiturient(Long id, String name, String surname, String address, String phoneNumber, int[] marks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.marks = marks;
    }

    public static Abiturient of(Long id, String name, String surname, String address, String phoneNumber, int[] marks) {
        return new Abiturient(id, name, surname, address, phoneNumber, marks);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public Integer calculateSum() {
        System.out.println(Arrays.stream(marks).sum());
        return Arrays.stream(marks).sum();
    }

    public int compareTo(Abiturient o) {
        return this.calculateSum().compareTo(o.calculateSum());
    }

    @Override
    public String toString() {
        return "Abiturient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", marks=" + Arrays.toString(marks) +
                '}';
    }
}