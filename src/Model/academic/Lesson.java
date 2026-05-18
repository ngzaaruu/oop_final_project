package Model.academic;

import Model.users.Teacher;
import Model.enums.LessonType;

import java.io.*;
import java.util.*;

public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

   
    private int id;
    private LessonType type;
    private Date date;
    private Teacher instructor;
    private final Course course;

    private List<Attendance> attendances;

    public Lesson(int id, LessonType type, Date date, Teacher instructor, Course course) {
        if (type == null || date == null || instructor == null || course == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.id = id;
        this.type = type;
        this.date = new Date(date.getTime());
        this.instructor = instructor;
        this.course = course;

        this.attendances = new ArrayList<>();

        course.addLesson(this);
    }

    public int getId() {
        return id;
    }

    public LessonType getType() {
        return type;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public Course getCourse() {
        return course;
    }

    public void setInstructor(Teacher instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        this.instructor = instructor;
    }

    public void setDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = new Date(date.getTime());
    }

    public void addAttendance(Attendance attendance) {
        if (attendance == null) return;

        if (!attendances.contains(attendance)) {
            attendances.add(attendance);
        }
    }

    public List<Attendance> getAttendances() {
        return new ArrayList<>(attendances);
    }

    @Override
    public String toString() {
        return "Lesson: " + type + " on " + date + " by " + instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

