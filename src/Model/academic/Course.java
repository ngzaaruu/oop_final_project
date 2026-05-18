package Model.academic;

import java.io.*;
import java.util.*;

import Model.users.Student;
import Model.users.Teacher;
import Model.enums.CourseType;

public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private int id;
    private String name;
    private CourseType type;
    private int credits;
    private String majorTarget;
    private int yearOfStudy;

    private List<Student> students;
    private List<Enrollment> enrollments;
    private List<Teacher> instructors;
    private List<Lesson> lessons;

    public Course(int id, String name, CourseType type, int credits, String majorTarget, int yearOfStudy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.credits = credits;
        this.majorTarget = majorTarget;
        this.yearOfStudy = yearOfStudy;

        this.students = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student == null) return;

        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        if (student == null) return;

        students.remove(student);
        Enrollment enrollment = findEnrollment(student);
        if (enrollment != null) {
            enrollment.deactivate();
            enrollments.remove(enrollment);
        }
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment == null) return;

        if (!equals(enrollment.getCourse())) {
            throw new IllegalArgumentException("Enrollment belongs to another course");
        }

        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }

        Student student = enrollment.getStudent();
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void removeEnrollment(Enrollment enrollment) {
        if (enrollment == null) return;

        enrollments.remove(enrollment);
        students.remove(enrollment.getStudent());
        enrollment.deactivate();
    }

    public Enrollment findEnrollment(Student student) {
        if (student == null) return null;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)) {
                return enrollment;
            }
        }

        return null;
    }

    public void addInstructor(Teacher teacher) {
        if (teacher == null) return;

        if (!instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    public void removeInstructor(Teacher teacher) {
        if (teacher == null) return;

        instructors.remove(teacher);
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) return;

        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public List<Teacher> getInstructors() {
        return new ArrayList<>(instructors);
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        this.name = name;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        if (type == null) {
            throw new IllegalArgumentException("Course type cannot be null");
        }
        this.type = type;
    }

    public String getMajorTarget() {
        return majorTarget;
    }

    public void setMajorTarget(String majorTarget) {
        if (majorTarget == null || majorTarget.isEmpty()) {
            throw new IllegalArgumentException("Major target cannot be empty");
        }
        this.majorTarget = majorTarget;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        if (yearOfStudy <= 0) {
            throw new IllegalArgumentException("Year of study must be positive");
        }
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String toString() {
        return "Course: " + name + " (" + credits + " credits)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

