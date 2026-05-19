package views;

import Model.academic.Course;
import Model.users.Student;
import Model.users.Teacher;

import java.util.List;

public class TeacherView {

    public void showMenu(Teacher teacher) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.printf( "║  Teacher: %-25s║%n", teacher.getFullName());
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. View my courses                ║");
        System.out.println("║  2. View students in course        ║");
        System.out.println("║  3. Put grade                      ║");
        System.out.println("║  4. Edit course info               ║");
        System.out.println("║  5. Send complaint                 ║");
        System.out.println("║  0. Logout                         ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("  > ");
    }

    public void showCourses(List<Course> courses) {
        if (courses.isEmpty()) { System.out.println("  [No courses]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬─────────┐");
        System.out.println("  │ #  │ Name                     │ Credits │");
        System.out.println("  ├────┼──────────────────────────┼─────────┤");
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.printf("  │ %-2d │ %-24s │ %-7d │%n",
                    i + 1, c.getName(), c.getCredits());
        }
        System.out.println("  └────┴──────────────────────────┴─────────┘");
    }

    public void showStudents(List<Student> students) {
        if (students.isEmpty()) { System.out.println("  [No students]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬──────────┬─────┐");
        System.out.println("  │ #  │ Name                     │ Major    │ GPA │");
        System.out.println("  ├────┼──────────────────────────┼──────────┼─────┤");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("  │ %-2d │ %-24s │ %-8s │ %-3.1f │%n",
                    i + 1, s.getFullName(), s.getMajor(), s.getGpa());
        }
        System.out.println("  └────┴──────────────────────────┴──────────┴─────┘");
    }

    public void showMessage(String msg) { System.out.println("  ✓ " + msg); }
    public void showError(String msg)   { System.out.println("  ✗ " + msg); }
}