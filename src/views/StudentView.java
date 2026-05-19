package views;

import Model.academic.Course;
import Model.academic.Mark;
import Model.users.Student;
import Model.users.Teacher;

import java.util.List;
import java.util.Map;

public class StudentView {

    public void showMenu(Student student) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.printf( "║  Student: %-25s║%n", student.getFullName());
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. View available courses         ║");
        System.out.println("║  2. Register for a course          ║");
        System.out.println("║  3. Drop a course                  ║");
        System.out.println("║  4. View my grades                 ║");
        System.out.println("║  5. View transcript                ║");
        System.out.println("║  6. View schedule                  ║");
        System.out.println("║  7. Rate a teacher                 ║");
        System.out.println("║  8. Teacher ranking                ║");
        System.out.println("║  0. Logout                         ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("  > ");
    }

    public void showCourses(List<Course> courses) {
        if (courses.isEmpty()) { System.out.println("  [No courses]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬─────────┐");
        System.out.println("    │ #  │ Name                     │ Credits │");
        System.out.println("    ├────┼──────────────────────────┼─────────┤");
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.printf("  │ %-2d │ %-24s │ %-7d │%n",
                    i + 1, c.getName(), c.getCredits());
        }
        System.out.println("    └────┴──────────────────────────┴─────────┘");
    }

    public void showMarks(Map<Course, Mark> marks) {
        if (marks.isEmpty()) { System.out.println("  [No grades yet]"); return; }
        System.out.println("\n  ┌──────────────────────────┬───────┬───────┐");
        System.out.println("    │ Course                   │ Total │ Grade │");
        System.out.println("    ├──────────────────────────┼───────┼───────┤");
        marks.forEach((course, mark) ->
                System.out.printf("  │ %-24s │ %-5.1f │ %-5s │%n",
                        course.getName(), mark.getTotal(), mark.getLetterGrade()));
        System.out.println("    └──────────────────────────┴───────┴───────┘");
    }

    public void showTranscript(String transcript) {
        System.out.println("\n" + transcript);
    }

    public void showSchedule(Student student) {
        System.out.println("\n" + student.getSchedule());
    }

    public void showTeacherRanking(List<Teacher> teachers, Map<Teacher, Double> avgRatings) {
        if (teachers.isEmpty()) { System.out.println("  [No teachers]"); return; }
        System.out.println("\n  ┌────┬──────────────────────────┬──────────┬────────┐");
        System.out.println("    │ #  │ Teacher                  │ Position │ Rating │");
        System.out.println("    ├────┼──────────────────────────┼──────────┼────────┤");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            double avg = avgRatings.getOrDefault(t, 0.0);
            System.out.printf(" │ %-2d │ %-24s │ %-8s │ %-6.2f │%n",
                    i + 1, t.getFullName(), t.getPosition(), avg);
        }
        System.out.println("    └────┴──────────────────────────┴──────────┴────────┘");
    }
    
    

    public void showMessage(String msg) { System.out.println("  ✓ " + msg); }
    public void showError(String msg)   { System.out.println("  ✗ " + msg); }
    
    
    
    
    
}