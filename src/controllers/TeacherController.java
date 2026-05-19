package controllers;

import Model.academic.Course;
import Model.academic.Mark;
import Model.enums.ComplaintUrgency;
import Model.service.ManagerService;
import Model.service.TeacherService;
import Model.users.Manager;
import Model.users.Student;
import Model.users.Teacher;
import views.TeacherView;

import java.util.List;
import java.util.Scanner;

public class TeacherController {

    private final TeacherService service;
    private final TeacherView view;
    private final Scanner scanner;

    public TeacherController(TeacherService service, TeacherView view, Scanner scanner) {
        this.service = service;
        this.view = view;
        this.scanner = scanner;
    }

    public void run(Teacher teacher) {
        boolean active = true;
        while (active) {
            view.showMenu(teacher);
            switch (scanner.nextLine().trim()) {
                case "1" -> view.showCourses(service.getMyCourses(teacher));
                case "2" -> showStudents(teacher);
                case "3" -> putMark(teacher);
                case "4" -> editCourse(teacher);
                case "5" -> sendComplaint(teacher);
                case "0" -> { view.showMessage("Logged out."); active = false; }
                default  -> view.showError("Unknown option");
            }
        }
    }

    private void showStudents(Teacher teacher) {
        Course course = pickCourse(teacher);
        if (course == null) return;
        view.showStudents(service.getStudentsInCourse(teacher, course));
    }

    private void putMark(Teacher teacher) {
        Course course = pickCourse(teacher);
        if (course == null) return;
        List<Student> students = service.getStudentsInCourse(teacher, course);
        view.showStudents(students);
        if (students.isEmpty()) return;

        System.out.print("  Student number: ");
        int sIdx = readInt() - 1;
        if (sIdx < 0 || sIdx >= students.size()) { view.showError("Invalid"); return; }

        System.out.print("  Attestation 1 (0-30): "); 
        double a1 = readDouble();
        
        System.out.print("  Attestation 2 (0-30): "); 
        double a2 = readDouble();
        
        System.out.print("  Final exam (0-40):     "); 
        double fn = readDouble();

        try {
            Mark mark = new Mark(a1, a2, fn, students.get(sIdx), course);
            service.putMark(teacher, students.get(sIdx), course, mark);
            view.showMessage("Mark saved.");
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void editCourse(Teacher teacher) {
        Course course = pickCourse(teacher);
        if (course == null) return;
        System.out.print("  New name: ");        String name    = scanner.nextLine().trim();
        System.out.print("  New credits (int): "); int credits  = readInt();
        service.manageCourse(teacher, course, name, credits);
        view.showMessage("Course updated.");
    }

    private void sendComplaint(Teacher teacher) {
       
        System.out.print("  Student name to complain about: ");
        String studentName = scanner.nextLine().trim();
        System.out.println("  Urgency: 1=LOW  2=MEDIUM  3=HIGH");
        System.out.print("  > ");
        ComplaintUrgency urgency = switch (readInt()) {
            case 2  -> ComplaintUrgency.MEDIUM;
            case 3  -> ComplaintUrgency.HIGH;
            default -> ComplaintUrgency.LOW;
        };
        System.out.print("  Details: ");
        String details = scanner.nextLine().trim();
        view.showMessage("Complaint prepared (LOW=" + urgency + "). Pass to ManagerController to submit.");
    }

    private Course pickCourse(Teacher teacher) {
        List<Course> courses = service.getMyCourses(teacher);
        view.showCourses(courses);
        if (courses.isEmpty()) return null;
        System.out.print("  Course number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) { view.showError("Invalid"); return null; }
        return courses.get(idx);
    }

    private int    readInt()    { 
    	try { 
    		return Integer.parseInt(scanner.nextLine().trim()); } 
    	catch (NumberFormatException e) { return -1; } 
    	}
    
    private double readDouble() { try { return Double.parseDouble(scanner.nextLine().trim()); } catch (NumberFormatException e) { return -1; } }
}