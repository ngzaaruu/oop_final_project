package Model.users;

import Model.exceptions.LowHIndexException;
import Model.research.ResearchPaper;
import Model.research.Researcher;
import Model.enums.Language;

import java.util.*;

public class GraduateStudent extends Student {

    private static final long serialVersionUID = 1L;

    private String thesisTopic;
    private Researcher supervisor;
    private String degreeType;
    private List<ResearchPaper> publishedDiplomaPapers;
    private boolean thesisSubmitted;

    public GraduateStudent(int id, String fullName, String email, String password,
                           String phoneNumber, Language language,
                           String studentId, String major, int yearOfStudy,
                           String thesisTopic, String degreeType) {

        super(id, fullName, email, password, phoneNumber, language, studentId, major, yearOfStudy);

        if (thesisTopic == null || thesisTopic.isEmpty()) {
            throw new IllegalArgumentException("Thesis topic cannot be empty");
        }

        this.thesisTopic = thesisTopic;
        this.degreeType = degreeType;
        this.publishedDiplomaPapers = new ArrayList<>();
        this.thesisSubmitted = false;
    }

    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor == null) {
            throw new LowHIndexException("Supervisor cannot be null");
        }

        if (supervisor.calculateHIndex() < 3) {
            throw new LowHIndexException("Supervisor h-index must be at least 3");
        }

        this.supervisor = supervisor;
    }

    public void submitThesis() {
        if (thesisSubmitted) return;

        this.thesisSubmitted = true;
        System.out.println(getFullName() + " submitted thesis: " + thesisTopic);
    }

    public boolean defendThesis() {
        if (!thesisSubmitted) {
            System.out.println("Thesis not submitted");
            return false;
        }

        if (supervisor == null) {
            System.out.println("No supervisor assigned");
            return false;
        }

        System.out.println(getFullName() + " defended thesis under supervisor: " + supervisor);
        return true;
    }

    public void addDiplomaPaper(ResearchPaper paper) {
        if (paper == null) return;

        if (!publishedDiplomaPapers.contains(paper)) {
            publishedDiplomaPapers.add(paper);
            addResearchPaper(paper);
        }
    }

    public List<ResearchPaper> getPublishedDiplomaPapers() {
        return new ArrayList<>(publishedDiplomaPapers);
    }

    public Researcher getSupervisor() {
        return supervisor;
    }
}
