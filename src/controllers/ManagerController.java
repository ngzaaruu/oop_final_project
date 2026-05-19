package controllers;

import Model.academic.Course;
import Model.communication.News;
import Model.communication.Request;
import Model.enums.NewsType;
import Model.service.ManagerService;
import Model.users.Manager;
import Model.users.Student;
import Model.users.Teacher;
import views.ManagerView;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ManagerController {

    private final ManagerService service;
    private final ManagerView view;
    private final Scanner scanner;

    public ManagerController(ManagerService service, ManagerView view, Scanner scanner) {
        this.service = service;
        this.view = view;
        this.scanner = scanner;
    }

    public void run(Manager manager) {
        boolean active = true;
        while (active) {
            view.showMenu(manager);
            switch (scanner.nextLine().trim()) {
                case "1"  -> assignTeacher(manager);
                case "2"  -> approveRegistration(manager);
                case "3"  -> rejectRegistration(manager);
                case "4"  -> viewStudents(manager);
                case "5"  -> view.showTeachers(service.getAllTeachers(manager));
                case "6"  -> view.showStatistics(service.getStatistics(manager));
                case "7"  -> publishNews(manager);
                case "8"  -> view.showNews(service.getAllNews(manager));
                case "9"  -> view.showRequests(service.viewRequests(manager));
                case "10" -> signRequest(manager);
                case "0"  -> { view.showMessage("Logged out."); active = false; }
                default   -> view.showError("Unknown option");
            }
        }
    }

    private void assignTeacher(Manager manager) {
        List<Teacher> teachers = service.getAllTeachers(manager);
        view.showTeachers(teachers);
        if (teachers.isEmpty()) return;
        System.out.print("  Teacher number: ");
        int tIdx = readInt() - 1;
        if (tIdx < 0 || tIdx >= teachers.size()) { view.showError("Invalid"); return; }

        List<Course> courses = service.getAllCourses(manager);
        view.showCourses(courses);
        if (courses.isEmpty()) { view.showError("No courses"); return; }
        System.out.print("  Course number: ");
        int cIdx = readInt() - 1;
        if (cIdx < 0 || cIdx >= courses.size()) { view.showError("Invalid"); return; }

        service.assignTeacherToCourse(manager, teachers.get(tIdx), courses.get(cIdx));
        view.showMessage("Teacher assigned.");
    }

    private void approveRegistration(Manager manager) {
        List<Student> pending = service.getPendingStudents();
        view.showPendingStudents(pending);
        if (pending.isEmpty()) return;
        System.out.print("  Student number: ");
        int sIdx = readInt() - 1;
        if (sIdx < 0 || sIdx >= pending.size()) { view.showError("Invalid"); return; }
        Student student = pending.get(sIdx);

        List<Course> courses = student.getCourses();
        view.showCourses(courses);
        if (courses.isEmpty()) return;
        System.out.print("  Course number: ");
        int cIdx = readInt() - 1;
        if (cIdx < 0 || cIdx >= courses.size()) { view.showError("Invalid"); return; }

        boolean ok = service.approveRegistration(manager, student, courses.get(cIdx));
        if (ok) view.showMessage("Approved."); else view.showError("Could not approve.");
    }

    private void rejectRegistration(Manager manager) {
        List<Student> pending = service.getPendingStudents();
        view.showPendingStudents(pending);
        if (pending.isEmpty()) return;
        System.out.print("  Student number: ");
        int sIdx = readInt() - 1;
        if (sIdx < 0 || sIdx >= pending.size()) { view.showError("Invalid"); return; }
        Student student = pending.get(sIdx);

        List<Course> courses = student.getCourses();
        view.showCourses(courses);
        if (courses.isEmpty()) return;
        System.out.print("  Course number: ");
        int cIdx = readInt() - 1;
        if (cIdx < 0 || cIdx >= courses.size()) { view.showError("Invalid"); return; }

        service.rejectRegistration(manager, student, courses.get(cIdx));
        view.showMessage("Rejected.");
    }

    private void viewStudents(Manager manager) {
        System.out.println("  Sort: 1=Name  2=GPA  3=Year  [other=none]");
        System.out.print("  > ");
        Comparator<Student> cmp = switch (scanner.nextLine().trim()) {
            case "1" -> Comparator.comparing(Student::getFullName);
            case "2" -> Comparator.comparingDouble(Student::getGpa).reversed();
            case "3" -> Comparator.comparingInt(Student::getYearOfStudy);
            default  -> null;
        };
        view.showStudents(service.getAllStudents(manager, cmp));
    }

    private void publishNews(Manager manager) {
        System.out.print("  Title: ");   String title   = scanner.nextLine().trim();
        System.out.print("  Content: "); String content = scanner.nextLine().trim();
        System.out.println("  Type: 1=GENERAL  2=RESEARCH  3=ANNOUNCEMENT");
        System.out.print("  > ");
        NewsType type = switch (readInt()) {
            case 2  -> NewsType.RESEARCH;
            case 3  -> NewsType.ANNOUNCEMENT;
            default -> NewsType.GENERAL;
        };
        service.publishNews(manager, title, content, type);
        view.showMessage("News published.");
    }

    private void signRequest(Manager manager) {
        List<Request> requests = service.viewRequests(manager);
        view.showRequests(requests);
        if (requests.isEmpty()) return;
        System.out.print("  Request number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= requests.size()) { view.showError("Invalid"); return; }
        service.signRequest(manager, requests.get(idx));
        view.showMessage("Signed.");
    }

    private int readInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}