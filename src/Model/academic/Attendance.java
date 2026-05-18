package Model.academic;

import Model.users.Student;
import Model.enums.AttendanceStatus;

import java.io.*;
import java.util.*;

public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

   

    private final Student student;
    private final Lesson lesson;
    private AttendanceStatus status;

    public Attendance(Student student, Lesson lesson, AttendanceStatus status) {
        if (student == null || lesson == null || status == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.student = student;
        this.lesson = lesson;
        this.status = status;

        student.addAttendance(this);
        lesson.addAttendance(this);
    }

    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance: " + student + " - " + lesson + " (" + status + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance that = (Attendance) o;
        return student.equals(that.student) && lesson.equals(that.lesson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, lesson);
    }
}

