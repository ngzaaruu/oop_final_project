package Model.service;

import Model.academic.Course;
import Model.academic.Mark;
import Model.communication.Request;
import Model.enums.ComplaintUrgency;
import Model.users.Manager;
import Model.users.Student;
import Model.users.Teacher;

import java.util.List;

public class TeacherService {

    private final DataStorage storage;
    private final Logger logger;

    public TeacherService(DataStorage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
    }


    public List<Course> getMyCourses(Teacher teacher) {
        logger.log(teacher, "Viewed own courses");
        return teacher.viewCourses
        ();
    }


    public List<Student> getStudentsInCourse(Teacher teacher, Course course) {
        logger.log(teacher, "Viewed students in: " + course.getName());
        return teacher.viewStudents(course);
    }


    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        teacher.putMark(student, course, mark);
        logger.log(teacher, "Put mark for " + student.getFullName() + " in " + course.getName());
    }


    public void teachCourse(Teacher teacher, Course course) {
        teacher.teachCourse(course);
        logger.log(teacher, "Assigned to course: " + course.getName());
    }

   
    public void manageCourse(Teacher teacher, Course course, String newName, int newCredits) {
        teacher.manageCourse(course, newName, newCredits);
        logger.log(teacher, "Updated course: " + newName);
    }

   
    public Request sendComplaint(Teacher teacher, Manager dean,
                                  Student student, ComplaintUrgency urgency, String details) {
        Request req = teacher.sendComplaint(dean, student, urgency, details);
        logger.log(teacher, "Sent complaint about " + student.getFullName());
        return req;
    }

 
    public String getCourseInfo(Course course) {
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(course.getName())
          .append(" | Credits: ").append(course.getCredits())
          .append(" | Students: ").append(course.getStudents().size());
        return sb.toString();
    }
}