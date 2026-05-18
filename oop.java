package Model.academic;

import Model.users.Student;

import java.io.*;
import java.util.*;

public class StudentOrganization implements Serializable {

    private Student head;
    private String name;
    private List<Student> members;

    public StudentOrganization(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(Student student) {
        if (student == null) return;

        if (!members.contains(student)) {
            members.add(student);
        }
    }

    public void removeMember(Student student) {
        if (student == null) return;

        members.remove(student);

       
        if (student.equals(head)) {
            head = null;
        }
    }

    public void setHead(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Head cannot be null");
        }

       
        if (!members.contains(student)) {
            members.add(student);
        }

        this.head = student;
    }

    public Student getHead() {
        return head;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public String toString() {
        return "Organization: " + name + ", members: " + members.size();
    }
}
