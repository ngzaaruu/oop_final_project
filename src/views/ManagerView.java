package views;

import Model.academic.Course;
import Model.communication.News;
import Model.communication.Request;
import Model.users.Manager;
import Model.users.Student;
import Model.users.Teacher;

import java.util.List;

public class ManagerView {

    public void showMenu(Manager manager) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.printf( "║  Manager: %-25s║%n", manager.getFullName());
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. Assign teacher to course       ║");
        System.out.println("║  2. Approve student registration   ║");
        System.out.println("║  3. Reject student registration    ║");
        System.out.println("║  4. View all students              ║");
        System.out.println("║  5. View all teachers              ║");
        System.out.println("║  6. View statistics                ║");
        System.out.println("║  7. Publish news                   ║");
        System.out.println("║  8. View news                      ║");
        System.out.println("║  9. View requests                  ║");
        System.out.println("║ 10. Sign a request                 ║");
        System.out.println("║  0. Logout                         ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("  > ");
    }

    public void showStudents(List<Student> students) {
        if (students.isEmpty()) { System.out.println("  [No students]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬──────────┬──────┬─────┐");
        System.out.println("  │ #  │ Name                     │ Major    │ Year │ GPA │");
        System.out.println("  ├────┼──────────────────────────┼──────────┼──────┼─────┤");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("  │ %-2d │ %-24s │ %-8s │ %-4d │ %-3.1f │%n",
                    i + 1, s.getFullName(), s.getMajor(), s.getYearOfStudy(), s.getGpa());
        }
        System.out.println("  └────┴──────────────────────────┴──────────┴──────┴─────┘");
    }

    public void showTeachers(List<Teacher> teachers) {
        if (teachers.isEmpty()) { System.out.println("  [No teachers]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬──────────────┐");
        System.out.println("  │ #  │ Name                     │ Position     │");
        System.out.println("  ├────┼──────────────────────────┼──────────────┤");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            System.out.printf("  │ %-2d │ %-24s │ %-12s │%n",
                    i + 1, t.getFullName(), t.getPosition());
        }
        System.out.println("  └────┴──────────────────────────┴──────────────┘");
    }

    public void showCourses(List<Course> courses) {
        if (courses.isEmpty()) { System.out.println("  [No courses]"); return; }
        for (int i = 0; i < courses.size(); i++)
            System.out.printf("  %d. %s (%d cr.)%n",
                    i + 1, courses.get(i).getName(), courses.get(i).getCredits());
    }

    public void showPendingStudents(List<Student> students) {
        if (students.isEmpty()) { System.out.println("  [No pending registrations]"); return; }
        System.out.println("\n  Pending registrations:");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("  %d. %s → courses: %s%n",
                    i + 1, s.getFullName(), s.getCourses());
        }
    }

    public void showNews(List<News> newsList) {
        if (newsList.isEmpty()) { System.out.println("  [No news]"); return; }
        for (int i = 0; i < newsList.size(); i++)
            System.out.printf("  %d. [%s] %s%n",
                    i + 1, newsList.get(i).getTopic(), newsList.get(i).getTitle());
    }

    public void showRequests(List<Request> requests) {
        if (requests.isEmpty()) { System.out.println("  [No requests]"); return; }
        for (int i = 0; i < requests.size(); i++) {
            Request r = requests.get(i);
            System.out.printf("  %d. [%s] %s: %s%n",
                    i + 1, r.getStatus(),
                    r.getSender().getFullName(),
                    r.getDescription());
        }
    }

    public void showStatistics(String stats) { System.out.println(stats); }
    public void showMessage(String msg) { System.out.println("  ✓ " + msg); }
    public void showError(String msg)   { System.out.println("  ✗ " + msg); }
}