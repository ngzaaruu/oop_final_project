package Model.service;

import Model.academic.Course;
import Model.academic.Mark;
import Model.exceptions.*;
import Model.users.Student;
import Model.users.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private final DataStorage storage;
    private final Logger logger;

    public StudentService(DataStorage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
    }

   
    public void registerCourse(Student student, Course course)
            throws MaxCreditsExceededException, TooManyFailsException, DuplicateRegistrationException {
        student.registerCourse(course);
        logger.log(student, "Requested registration: " + course.getName());
    }

    public void dropCourse(Student student, Course course) {
        student.dropCourse(course);
        logger.log(student, "Dropped course: " + course.getName());
    }

  
    public Map<Course, Mark> viewMarks(Student student) {
        logger.log(student, "Viewed marks");
        return student.viewMarks();
    }


    public String getTranscript(Student student) {
        logger.log(student, "Viewed transcript");
        return student.getTranscript();
    }

   
    public void rateTeacher(Student student, Teacher teacher, int rating) {
        student.rateTeacher(teacher, rating);
        logger.log(student, "Rated teacher " + teacher.getFullName() + ": " + rating);
    }

  
    public List<Course> getAvailableCourses() {
        return storage.getCourses();
    }


    public List<Course> getEnrolledCourses(Student student) {
        return student.getCourses();
    }

  
    public List<Teacher> getTeacherRanking() {
        Map<Teacher, List<Integer>> allRatings = new HashMap<>();

        for (var user : storage.getUsers()) {
            if (user instanceof Student s) {
                s.getTeacherRatings().forEach((teacher, rating) ->
                        allRatings.computeIfAbsent(teacher, k -> new ArrayList<>()).add(rating));
            }
        }

        return allRatings.entrySet().stream()
                .sorted((a, b) -> {
                    double avgA = a.getValue().stream().mapToInt(i -> i).average().orElse(0);
                    double avgB = b.getValue().stream().mapToInt(i -> i).average().orElse(0);
                    return Double.compare(avgB, avgA);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

 
    public double getAverageRating(Teacher teacher) {
        List<Integer> ratings = new ArrayList<>();
        for (var user : storage.getUsers()) {
            if (user instanceof Student s) {
                Integer r = s.getTeacherRatings().get(teacher);
                if (r != null) ratings.add(r);
            }
        }
        return ratings.stream().mapToInt(i -> i).average().orElse(0.0);
    }
}