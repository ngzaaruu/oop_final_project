package controllers;

import Model.academic.Course;
import Model.exceptions.*;
import Model.service.StudentService;
import Model.users.Student;
import Model.users.Teacher;
import views.StudentView;

import java.util.*;

public class StudentController {

    private final StudentService service;
    private final StudentView view;
    private final Scanner scanner;

    public StudentController(StudentService service, StudentView view, Scanner scanner) {
        this.service = service;
        this.view = view;
        this.scanner = scanner;
    }

    public void run(Student student) {
        boolean active = true;
        while (active) {
            view.showMenu(student);
            switch (scanner.nextLine().trim()) {
                case "1" -> view.showCourses(service.getAvailableCourses());
                case "2" -> registerCourse(student);
                case "3" -> dropCourse(student);
                case "4" -> view.showMarks(service.viewMarks(student));
                case "5" -> view.showTranscript(service.getTranscript(student));
                case "6" -> view.showSchedule(student);
                case "7" -> rateTeacher(student);
                case "8" -> showRanking(student);
                case "0" -> { view.showMessage("Logged out."); active = false; }
                default  -> view.showError("Unknown option");
            }
        }
    }

    private void registerCourse(Student student) {
        List<Course> courses = service.getAvailableCourses();
        view.showCourses(courses);
        if (courses.isEmpty()) return;
        System.out.print("  Course number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) { view.showError("Invalid selection"); return; }
        try {
            service.registerCourse(student, courses.get(idx));
            view.showMessage("Registered: " + courses.get(idx).getName());
        } catch (DuplicateRegistrationException e) {
            view.showError("Already registered: " + e.getMessage());
        } catch (MaxCreditsExceededException e) {
            view.showError("Too many credits: " + e.getMessage());
        } catch (TooManyFailsException e) {
            view.showError("Too many fails: " + e.getMessage());
        }
    }

    private void dropCourse(Student student) {
        List<Course> courses = service.getEnrolledCourses(student);
        view.showCourses(courses);
        if (courses.isEmpty()) return;
        System.out.print("  Course number to drop: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) { view.showError("Invalid selection"); return; }
        service.dropCourse(student, courses.get(idx));
        view.showMessage("Dropped: " + courses.get(idx).getName());
    }

    private void rateTeacher(Student student) {
        List<Teacher> teachers = service.getTeacherRanking();
        Map<Teacher, Double> avgMap = new HashMap<>();
        teachers.forEach(t -> avgMap.put(t, service.getAverageRating(t)));
        view.showTeacherRanking(teachers, avgMap);
        if (teachers.isEmpty()) return;
        System.out.print("  Teacher number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= teachers.size()) { view.showError("Invalid selection"); return; }
        System.out.print("  Rating (1-5): ");
        int rating = readInt();
        try {
            service.rateTeacher(student, teachers.get(idx), rating);
            view.showMessage("Rating saved!");
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    private void showRanking(Student student) {
        List<Teacher> teachers = service.getTeacherRanking();
        Map<Teacher, Double> avgMap = new HashMap<>();
        teachers.forEach(t -> avgMap.put(t, service.getAverageRating(t)));
        view.showTeacherRanking(teachers, avgMap);
    }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}