package Model.users;

import Model.academic.*;
import Model.enums.Language;
import Model.exceptions.*;
import Model.research.*;

import java.util.*;

public class Student extends User implements Researcher {

    private static final long serialVersionUID = 1L;
	protected String studentId;
    protected String major;
    protected int yearOfStudy;
    protected double gpa;
    protected int totalCredits;
    protected int failCount;

    protected List<Course> courses;
    protected List<Enrollment> enrollments;
    protected Map<Course, Mark> marks;
    protected Schedule schedule;
    protected List<StudentOrganization> organizations;
    protected Map<Teacher, Integer> teacherRatings;

    protected List<ResearchPaper> researchPapers;
    protected List<ResearchProject> researchProjects;

    public Student(int id, String fullName, String email, String password,
                   String phoneNumber, Language language,
                   String studentId, String major, int yearOfStudy) {

        super(id, fullName, email, password, phoneNumber, language);

        this.studentId = studentId;
        this.major = major;
        this.yearOfStudy = yearOfStudy;

        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.marks = new HashMap<>();
        this.organizations = new ArrayList<>();
        this.teacherRatings = new HashMap<>();
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();

       
        this.schedule = new Schedule(this);
    }

    public void registerCourse(Course course)
            throws MaxCreditsExceededException,
                   TooManyFailsException,
                   DuplicateRegistrationException {

        if (course == null) return;

        if (courses.contains(course)) {
            throw new DuplicateRegistrationException("Already registered");
        }

        if (totalCredits + course.getCredits() > 21) {
            throw new MaxCreditsExceededException("Credit limit exceeded");
        }

        if (failCount > 3) {
            throw new TooManyFailsException("Too many fails");
        }

        Enrollment enrollment = new Enrollment(this, course);
        courses.add(course);
        enrollments.add(enrollment);
        totalCredits += course.getCredits();
        course.addEnrollment(enrollment);
    }

    public void dropCourse(Course course) {
        if (course != null && courses.remove(course)) {
            totalCredits -= course.getCredits();
            Enrollment enrollment = findEnrollment(course);
            if (enrollment != null) {
                enrollment.deactivate();
                enrollments.remove(enrollment);
            }
            course.removeStudent(this);
        }
    }

    public Map<Course, Mark> viewMarks() {
        return new HashMap<>(marks);
    }

    public void addMark(Course course, Mark mark) {
        if (course != null && mark != null) {
            Mark previousMark = marks.get(course);

            if (previousMark != null && previousMark.isFail() && !mark.isFail()) {
                failCount--;
            } else if ((previousMark == null || !previousMark.isFail()) && mark.isFail()) {
                failCount++;
            }

            marks.put(course, mark);
            Enrollment enrollment = findEnrollment(course);
            if (enrollment != null) {
                enrollment.setMark(mark);
            }
        }
    }
    
    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public Enrollment findEnrollment(Course course) {
        if (course == null) return null;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                return enrollment;
            }
        }

        return null;
    }

    public void viewTranscript() {
        System.out.println(getTranscript());
    }

    public String getTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ").append(getFullName()).append("\n");

        if (marks.isEmpty()) {
            transcript.append("No marks yet.");
            return transcript.toString();
        }

        double total = 0;
        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            transcript.append(e.getKey().getName())
                    .append(" -> ")
                    .append(e.getValue())
                    .append("\n");
            total += e.getValue().getTotal();
        }

        transcript.append("Average: ").append(total / marks.size());
        return transcript.toString();
    }

    public String viewTeacherInfo(Course course) {
        if (course == null) {
            return "Course not found";
        }

        StringBuilder info = new StringBuilder();
        info.append("Teachers for ").append(course.getName()).append(":\n");

        List<Teacher> teachers = course.getInstructors();
        if (teachers.isEmpty()) {
            info.append("No teachers assigned.");
            return info.toString();
        }

        for (Teacher teacher : teachers) {
            info.append(teacher.getFullName())
                    .append(" | position: ")
                    .append(teacher.getPosition())
                    .append(" | department: ")
                    .append(teacher.getDepartment())
                    .append("\n");
        }

        return info.toString();
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        teacherRatings.put(teacher, rating);
    }

    public void joinOrganization(StudentOrganization org) {
        if (org != null && !organizations.contains(org)) {
            organizations.add(org);
            org.addMember(this);
        }
    }

    public List<StudentOrganization> getOrganizations() {
        return new ArrayList<>(organizations);
    }

    public Map<Teacher, Integer> getTeacherRatings() {
        return new HashMap<>(teacherRatings);
    }
    
    private List<Attendance> attendances = new ArrayList<>();

    public void addAttendance(Attendance attendance) {
        if (attendance != null && !attendances.contains(attendance)) {
            attendances.add(attendance);
        }
    }

    public List<Attendance> getAttendances() {
        return new ArrayList<>(attendances);
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
        } catch (NotResearcherException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void publishPaper(ResearchPaper paper) {
        addResearchPaper(paper);
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public double getGpa() {
        return gpa;
    }

    public int getFailCount() {
        return failCount;
    }

    public String getMajor() {
        return major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

	public String getSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
}

