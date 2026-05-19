package Model.service;

import Model.academic.Course;
import Model.communication.News;
import Model.communication.Request;
import Model.enums.NewsType;
import Model.users.Manager;
import Model.users.Student;
import Model.users.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class ManagerService {

    private final DataStorage storage;
    private final Logger logger;

    public ManagerService(DataStorage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
    }

  
    public void assignTeacherToCourse(Manager manager, Teacher teacher, Course course) {
        manager.assignCourseToTeacher(course, teacher);
        logger.log(manager, "Assigned " + teacher.getFullName() + " to " + course.getName());
    }

    
    public boolean approveRegistration(Manager manager, Student student, Course course) {
        boolean result = manager.approveRegistration(student, course);
        if (result) {
            logger.log(manager, "Approved " + student.getFullName() + " for " + course.getName());
        } else {
            logger.log(manager, "Failed to approve " + student.getFullName() + " for " + course.getName());
        }
        return result;
    }

    
    public void rejectRegistration(Manager manager, Student student, Course course) {
        student.dropCourse(course);
        logger.log(manager, "Rejected " + student.getFullName() + " from " + course.getName());
    }

    
    public void addCourse(Manager manager, Course course) {
        manager.addCourseForRegistration(course);
        storage.addCourse(course);
        logger.log(manager, "Added course: " + course.getName());
    }

    public List<Student> getAllStudents(Manager manager) {
        logger.log(manager, "Viewed all students");
        return storage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());
    }

   
    public List<Student> getAllStudents(Manager manager, Comparator<Student> comparator) {
        List<Student> list = getAllStudents(manager);
        if (comparator != null) list.sort(comparator);
        return list;
    }

   
    public List<Teacher> getAllTeachers(Manager manager) {
        logger.log(manager, "Viewed all teachers");
        return storage.getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .collect(Collectors.toList());
    }

   
    public List<Course> getAllCourses(Manager manager) {
        logger.log(manager, "Viewed all courses");
        return storage.getCourses();
    }

 
    public List<Student> getPendingStudents() {
        return storage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .filter(s -> !s.getCourses().isEmpty())
                .collect(Collectors.toList());
    }

    public String getStatistics(Manager manager) {
        logger.log(manager, "Viewed statistics");
        List<Student> students = getAllStudents(manager);
        StringBuilder sb = new StringBuilder("=== Statistics ===\n");

        Map<String, Long> byMajor = students.stream()
                .collect(Collectors.groupingBy(Student::getMajor, Collectors.counting()));
        sb.append("--- By Major ---\n");
        byMajor.forEach((major, count) ->
                sb.append(String.format("  %-20s : %d%n", major, count)));

        Map<Integer, Long> byYear = students.stream()
                .collect(Collectors.groupingBy(Student::getYearOfStudy, Collectors.counting()));
        sb.append("--- By Year ---\n");
        byYear.forEach((year, count) ->
                sb.append(String.format("  Year %-2d : %d students%n", year, count)));

        OptionalDouble avgGpa = students.stream()
                .mapToDouble(Student::getGpa).average();
        sb.append(String.format("Average GPA: %.2f%n", avgGpa.orElse(0.0)));

        return sb.toString();
    }

 
    public void publishNews(Manager manager, String title, String content, NewsType type) {
        News news = new News(title, content, type, manager);
        manager.manageNews(news);
        storage.addNews(news);
        logger.log(manager, "Published news: " + title);
    }

    public List<News> getAllNews(Manager manager) {
        logger.log(manager, "Viewed news");
        return storage.getNews();
    }

  
    public List<Request> viewRequests(Manager manager) {
        logger.log(manager, "Viewed requests");
        return manager.viewRequests();
    }

    public void signRequest(Manager manager, Request request) {
        manager.signRequest(request);
        logger.log(manager, "Signed request: " + request.getDescription());
    }
}