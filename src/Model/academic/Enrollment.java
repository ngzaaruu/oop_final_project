package Model.academic;

import Model.users.Student;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Enrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private Course course;
    private Mark mark;
    private Date enrolledAt;
    private boolean active;

    public Enrollment(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course cannot be null");
        }

        this.student = student;
        this.course = course;
        this.enrolledAt = new Date();
        this.active = true;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        if (mark == null) {
            throw new IllegalArgumentException("Mark cannot be null");
        }
        if (!student.equals(mark.getStudent()) || !course.equals(mark.getCourse())) {
            throw new IllegalArgumentException("Mark must belong to this enrollment");
        }
        this.mark = mark;
    }

    public Date getEnrolledAt() {
        return new Date(enrolledAt.getTime());
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public String toString() {
        return student.getFullName() + " enrolled in " + course.getName() +
                (mark == null ? "" : " | " + mark);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return student.equals(that.student) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}

