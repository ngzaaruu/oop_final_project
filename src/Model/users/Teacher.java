package Model.users;

import Model.academic.*;
import Model.communication.Request;
import Model.enums.ComplaintUrgency;

import Model.enums.Language;
import Model.enums.TeacherPosition;
import Model.enums.UrgencyLevel;
import Model.research.*;

import java.math.BigDecimal;
import java.util.*;

public class Teacher extends Employee implements Researcher {

    private static final long serialVersionUID = 1L;
	private String teacherId;
    private TeacherPosition position;

    private List<Course> courses;
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;

    public Teacher(int id, String fullName, String email, String password,
                   String phoneNumber, Language language,
                   String employeeId, BigDecimal salary,
                   String officeNumber, String department,
                   String teacherId, String position) {

        this(id, fullName, email, password, phoneNumber, language,
                employeeId, salary, officeNumber, department,
                teacherId, TeacherPosition.valueOf(position));
    }

    public Teacher(int id, String fullName, String email, String password,
                   String phoneNumber, Language language,
                   String employeeId, BigDecimal salary,
                   String officeNumber, String department,
                   String teacherId, TeacherPosition position) {

    	super(id, fullName, email, password, phoneNumber, language,
    		      employeeId, salary, officeNumber, department);

        this.teacherId = teacherId;
        this.position = position == null ? TeacherPosition.LECTOR : position;

        this.courses = new ArrayList<>();
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
    }

    public void teachCourse(Course course) {
        if (course == null) return;

        if (!courses.contains(course)) {
            courses.add(course);
            course.addInstructor(this); 
        }
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (student != null && course != null && mark != null) {
            student.addMark(course, mark);
        }
    }

    public List<Student> viewStudents(Course course) {
        if (course == null) return new ArrayList<>();

        return new ArrayList<>(course.getStudents());
    }

    public void sendMessage(User receiver, String text) {
        if (receiver == null || text == null || text.isEmpty()) return;

        super.sendMessage(receiver, text); 
    }

    public List<Course> viewCourses() {
        for (Course c : courses) {
            System.out.println(c.getName());
        }
        return courses;
    }

   

    public void manageCourse(Course course, String newName, int newCredits) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        course.setName(newName);
        course.setCredits(newCredits);
    }

    public void updateCourseTarget(Course course, String majorTarget, int yearOfStudy) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        course.setMajorTarget(majorTarget);
        course.setYearOfStudy(yearOfStudy);
    }

    public Request sendComplaint(Manager dean, Student student, ComplaintUrgency urgency, String details) {
        if (dean == null || student == null || urgency == null) {
            throw new IllegalArgumentException("Dean, student and urgency cannot be null");
        }

        String text = "Complaint about " + student.getFullName() +
                " | urgency: " + urgency +
                " | details: " + (details == null ? "" : details);
        Request request = new Request(text, this);
        dean.addRequest(request);
        return request;
    }

    public Request sendComplaint(Student student, UrgencyLevel urgency) {
        if (student == null || urgency == null) {
            throw new IllegalArgumentException("Student and urgency cannot be null");
        }

        String text = "Complaint about " + student.getFullName() + " | urgency: " + urgency;
        return new Request(text, this);
    }


    @Override
    public int calculateHIndex() {
        List<Integer> citations = new ArrayList<>();

        for (ResearchPaper p : researchPapers) {
            citations.add(p.getCitations());
        }

        citations.sort(Collections.reverseOrder());

        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                h = i + 1;
            }
        }

        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> copy = new ArrayList<>(researchPapers);
        copy.sort(comparator);

        for (ResearchPaper p : copy) {
            System.out.println(p);
        }
    }

    @Override
    public void addResearchPaper(ResearchPaper paper) {
        if (paper != null && !researchPapers.contains(paper)) {
            researchPapers.add(paper);
        }
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return new ArrayList<>(researchPapers);
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return new ArrayList<>(researchProjects);
    }

    @Override
    public void joinProject(ResearchProject project) {
        if (project == null) return;

        try {
            project.addParticipant(this);
            researchProjects.add(project);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        addResearchPaper(paper);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }

    @Override
    public String toString() {
        return "Teacher: " + getFullName() + " [" + teacherId + "] (" + position + ")";
    }
}

