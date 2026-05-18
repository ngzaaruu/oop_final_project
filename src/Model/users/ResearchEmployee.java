package Model.users;

import Model.enums.Language;
import Model.research.ResearchPaper;
import Model.research.ResearchProject;
import Model.research.Researcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResearchEmployee extends Employee implements Researcher {

    private static final long serialVersionUID = 1L;

    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;

    public ResearchEmployee(int id, String fullName, String email, String password,
                            String phoneNumber, Language language,
                            String employeeId, BigDecimal salary,
                            String officeNumber, String department) {
        super(id, fullName, email, password, phoneNumber, language,
                employeeId, salary, officeNumber, department);

        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citations = new ArrayList<>();

        for (ResearchPaper paper : researchPapers) {
            citations.add(paper.getCitations());
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

        for (ResearchPaper paper : copy) {
            System.out.println(paper);
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
        if (project == null) {
            return;
        }

        try {
            project.addParticipant(this);
            if (!researchProjects.contains(project)) {
                researchProjects.add(project);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        addResearchPaper(paper);
    }
}

