package Model.research;

import java.util.Comparator;
import java.util.List;
import java.io.Serializable;

public class ResearcherDecorator implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;
    protected Researcher researcher;
    private String title;

    public ResearcherDecorator(Researcher researcher) {
        this(researcher, "Decorated Researcher");
    }

    public ResearcherDecorator(Researcher researcher, String title) {
        if (researcher == null) {
            throw new IllegalArgumentException("Researcher cannot be null");
        }
        this.researcher = researcher;
        this.title = (title == null || title.isEmpty()) ? "Decorated Researcher" : title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int calculateHIndex() {
        return researcher.calculateHIndex();
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("=== " + title + " papers ===");
        researcher.printPapers(comparator);
    }

    public String getResearchProfile() {
        return title + ": h-index=" + calculateHIndex()
                + ", papers=" + getResearchPapers().size()
                + ", projects=" + getResearchProjects().size();
    }

    @Override
    public void addResearchPaper(ResearchPaper paper) {
        researcher.addResearchPaper(paper);
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return researcher.getResearchPapers();
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return researcher.getResearchProjects();
    }

    @Override
    public void joinProject(ResearchProject project) {
        researcher.joinProject(project);
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        researcher.publishPaper(paper);
    }
}

