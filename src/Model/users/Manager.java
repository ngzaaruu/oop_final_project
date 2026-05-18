package Model.users;

import Model.academic.Course;
import Model.academic.Report;
import Model.communication.News;
import Model.communication.Request;
import Model.enums.Language;
import Model.enums.ManagerType;
import Model.service.ReportService;

import java.util.*;

public class Manager extends Employee {

    private static final long serialVersionUID = 1L;

    private String managerId;
    private ManagerType managerType;
    private List<Request> requests;

    public Manager(int id, String fullName, String email, String password,
                   String phoneNumber, Language language,
                   String employeeId, String managerId, String managerType) {

        this(id, fullName, email, password, phoneNumber, language,
                employeeId, managerId, ManagerType.valueOf(managerType));
    }

    public Manager(int id, String fullName, String email, String password,
                   String phoneNumber, Language language,
                   String employeeId, String managerId, ManagerType managerType) {

        super(id, fullName, email, password, phoneNumber, language,
              employeeId, null, null, null);

        this.managerId = managerId;
        this.managerType = managerType;
        this.requests = new ArrayList<>();
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) return;

        course.addInstructor(teacher);
        teacher.teachCourse(course);
    }

    public boolean approveRegistration(Student student, Course course) {
        if (student == null || course == null) return false;

        course.addStudent(student);
        return true;
    }

    public void addCourseForRegistration(Course course) {
        if (course != null) {
            System.out.println("Course opened: " + course.getName());
        }
    }

    public Report createAcademicPerformanceReport(List<Student> students) {
        return new ReportService().generateDepartmentReport(students);
    }

    public void manageNews(News news) {
        if (news != null) {
            System.out.println("News managed: " + news.getTitle());
        }
    }

    public List<Student> viewAllStudents(List<Student> students, Comparator<Student> comparator) {
        List<Student> copy = new ArrayList<>(students);
        copy.sort(comparator);
        return copy;
    }

    public List<Teacher> viewAllTeachers(List<Teacher> teachers) {
        return new ArrayList<>(teachers);
    }

    public List<Request> viewRequests() {
        return new ArrayList<>(requests);
    }

    public void addRequest(Request request) {
        if (request != null && !requests.contains(request)) {
            requests.add(request);
        }
    }

    public void signRequest(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (managerType != ManagerType.DEAN && managerType != ManagerType.RECTOR) {
            throw new IllegalStateException("Only dean or rector can sign requests");
        }
        request.sign(this);
    }

    public String getManagerId() {
        return managerId;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    @Override
    public String toString() {
        return "Manager: " + getFullName() + " [" + managerId + "] (" + managerType + ")";
    }
}

