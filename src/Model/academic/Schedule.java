package Model.academic;

import Model.users.Student;

import java.io.*;
import java.util.*;

public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

   
    private final Student student;
    private List<Lesson> lessons;

    public Schedule(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.student = student;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) return;

        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void removeLesson(Lesson lesson) {
        if (lesson == null) return;

        lessons.remove(lesson);
    }

    public List<Lesson> getDailySchedule(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        List<Lesson> result = new ArrayList<>();

        for (Lesson l : lessons) {
            if (isSameDay(l.getDate(), date)) {
                result.add(l);
            }
        }

        return result;
    }

    public List<Lesson> getAllLessons() {
        return new ArrayList<>(lessons);
    }

    private boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
               c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public String toString() {
        return "Schedule for student: " + student.getFullName();
    }
}

