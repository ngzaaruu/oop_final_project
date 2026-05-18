package Model.academic;

import Model.users.Student;

import java.io.*;
import java.util.*;

public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private List<Mark> marks;
    private Date generatedAt;

    public Report(Student student, List<Mark> marks) {
        if (student == null || marks == null) {
            throw new IllegalArgumentException("Student and marks cannot be null");
        }

        for (Mark m : marks) {
            if (!m.getStudent().equals(student)) {
                throw new IllegalArgumentException("Mark does not belong to this student");
            }
        }

        this.student = student;
        this.marks = new ArrayList<>(marks);
        this.generatedAt = new Date();
    }

    private Report(Student student, List<Mark> marks, boolean skipStudentCheck) {
        if (student == null || marks == null) {
            throw new IllegalArgumentException("Student and marks cannot be null");
        }
        this.student = student;
        this.marks = new ArrayList<>(marks);
        this.generatedAt = new Date();
    }

    public static Report departmentReport(Student baseStudent, List<Mark> marks) {
        return new Report(baseStudent, marks, true);
    }

    private double calculateAverage() {
        if (marks.isEmpty()) return 0;

        double sum = 0;
        for (Mark m : marks) {
            sum += m.getTotal();
        }

        return sum / marks.size();
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();

        sb.append("REPORT\n");
        sb.append("Student: ").append(student).append("\n");
        sb.append("Date: ").append(generatedAt).append("\n\n");

        for (Mark m : marks) {
            sb.append(m.getCourse().getName())
              .append(" -> ")
              .append(m.getTotal())
              .append(" (")
              .append(m.getLetterGrade())
              .append(")\n");
        }

        sb.append("\nAverage: ").append(calculateAverage());

        return sb.toString();
    }

    public double getAverageGrade() {
        return calculateAverage();
    }

    public Date getGeneratedAt() {
        return new Date(generatedAt.getTime());
    }

    public Student getStudent() {
        return student;
    }

    public List<Mark> getMarks() {
        return new ArrayList<>(marks);
    }

    @Override
    public String toString() {
        return generate();
    }
}

