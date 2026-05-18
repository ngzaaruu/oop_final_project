package Model.service;

import Model.academic.Report;
import Model.academic.Mark;
import Model.users.Student;
import Model.users.Employee;
import Model.research.Researcher;
import Model.research.ResearchPaper;

import java.util.*;

public class ReportService {

    public ReportService() {}

    public Report generateStudentReport(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        List<Mark> marks = new ArrayList<>(student.viewMarks().values());

        return new Report(student, marks);
    }

    public Report generateDepartmentReport(List<Student> students) {
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("Students list is empty");
        }

        List<Mark> allMarks = new ArrayList<>();
        Student base = students.get(0);

        for (Student s : students) {
            allMarks.addAll(s.viewMarks().values());
        }

        return Report.departmentReport(base, allMarks);
    }

    public void printTopResearchers(List<Researcher> researchers) {
        if (researchers == null) return;

        researchers.sort((r1, r2) ->
                Integer.compare(r2.calculateHIndex(), r1.calculateHIndex())
        );

        for (Researcher r : researchers) {
            System.out.println(r + " h-index: " + r.calculateHIndex());
        }
    }

    public void printTopResearchersBySchool(List<Researcher> researchers, String school) {
        Researcher researcher = findTopCitedResearcherBySchool(researchers, school);
        if (researcher == null) {
            System.out.println("No researchers found for school: " + school);
            return;
        }
        System.out.println(researcher + " citations: " + getTotalCitations(researcher));
    }

    public void printTopResearchersByYear(List<Researcher> researchers, int year) {
        Researcher researcher = findTopCitedResearcherByYear(researchers, year);
        if (researcher == null) {
            System.out.println("No researchers found for year: " + year);
            return;
        }
        System.out.println(researcher + " citations in " + year + ": " + getTotalCitationsByYear(researcher, year));
    }

    public void printAllPapers(List<Researcher> researchers, Comparator<ResearchPaper> comparator) {
        if (researchers == null) return;

        Set<ResearchPaper> uniquePapers = new LinkedHashSet<>();

        for (Researcher r : researchers) {
            uniquePapers.addAll(r.getResearchPapers());
        }

        List<ResearchPaper> papers = new ArrayList<>(uniquePapers);

        papers.sort(comparator);

        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    public Researcher findTopCitedResearcher(List<Researcher> researchers) {
        if (researchers == null || researchers.isEmpty()) {
            return null;
        }

        Researcher top = null;
        int maxCitations = -1;

        for (Researcher researcher : researchers) {
            int citations = getTotalCitations(researcher);
            if (citations > maxCitations) {
                maxCitations = citations;
                top = researcher;
            }
        }

        return top;
    }

    public Researcher findTopCitedResearcherBySchool(List<Researcher> researchers, String school) {
        if (researchers == null || school == null) {
            return null;
        }

        Researcher top = null;
        int maxCitations = -1;

        for (Researcher researcher : researchers) {
            if (!school.equalsIgnoreCase(getResearcherSchool(researcher))) {
                continue;
            }

            int citations = getTotalCitations(researcher);
            if (citations > maxCitations) {
                maxCitations = citations;
                top = researcher;
            }
        }

        return top;
    }

    public Researcher findTopCitedResearcherByYear(List<Researcher> researchers, int year) {
        if (researchers == null) {
            return null;
        }

        Researcher top = null;
        int maxCitations = -1;

        for (Researcher researcher : researchers) {
            int citations = getTotalCitationsByYear(researcher, year);
            if (citations > maxCitations) {
                maxCitations = citations;
                top = researcher;
            }
        }

        return top;
    }

    public int getTotalCitations(Researcher researcher) {
        if (researcher == null) {
            return 0;
        }

        int total = 0;
        for (ResearchPaper paper : researcher.getResearchPapers()) {
            total += paper.getCitations();
        }
        return total;
    }

    public int getTotalCitationsByYear(Researcher researcher, int year) {
        if (researcher == null) {
            return 0;
        }

        int total = 0;
        Calendar calendar = Calendar.getInstance();

        for (ResearchPaper paper : researcher.getResearchPapers()) {
            if (paper.getPublicationDate() == null) {
                continue;
            }
            calendar.setTime(paper.getPublicationDate());
            if (calendar.get(Calendar.YEAR) == year) {
                total += paper.getCitations();
            }
        }

        return total;
    }

    private String getResearcherSchool(Researcher researcher) {
        if (researcher instanceof Student) {
            return ((Student) researcher).getMajor();
        }
        if (researcher instanceof Employee) {
            return ((Employee) researcher).getDepartment();
        }
        return "";
    }
}

