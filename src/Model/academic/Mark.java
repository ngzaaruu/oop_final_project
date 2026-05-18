package Model.academic;

import Model.users.Student;

import java.io.*;
import java.util.*;

public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

   

    private double attestation1;
    private double attestation2;
    private double finalExam;

    private final Student student;
    private final Course course;
    private double total;

    public Mark(double attestation1, double attestation2, double finalExam, Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and Course cannot be null");
        }

        validateScores(attestation1, attestation2, finalExam);

        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
        this.student = student;
        this.course = course;

        this.total = calculateTotal();

        student.addMark(course, this);
    }

    private void validateScores(double att1, double att2, double finalExam) {
        if (att1 < 0 || att1 > 30) {
            throw new IllegalArgumentException("Attestation1 must be between 0 and 30");
        }
        if (att2 < 0 || att2 > 30) {
            throw new IllegalArgumentException("Attestation2 must be between 0 and 30");
        }
        if (finalExam < 0 || finalExam > 40) {
            throw new IllegalArgumentException("Final exam must be between 0 and 40");
        }
    }

    public double calculateTotal() {
        total = attestation1 + attestation2 + finalExam;
        return total;
    }

    public String getLetterGrade() {
        double t = total;

        if (t >= 90) return "A";
        if (t >= 85) return "A-";
        if (t >= 80) return "B+";
        if (t >= 75) return "B";
        if (t >= 70) return "B-";
        if (t >= 65) return "C+";
        if (t >= 60) return "C";
        if (t >= 55) return "C-";
        if (t >= 50) return "D";
        return "F";
    }

    public boolean isFail() {
        return getLetterGrade().equals("F");
    }

    public double getTotal() {
        return total;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Mark: " + total + " (" + getLetterGrade() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return student.equals(mark.student) && course.equals(mark.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}

