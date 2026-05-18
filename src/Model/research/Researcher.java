package Model.research;

import java.util.Comparator;
import java.util.List;

public interface Researcher {

    int calculateHIndex();

    void printPapers(Comparator<ResearchPaper> comparator);

    void addResearchPaper(ResearchPaper paper);

    List<ResearchPaper> getResearchPapers();

    List<ResearchProject> getResearchProjects();

    void joinProject(ResearchProject project);

    void publishPaper(ResearchPaper paper);
    
}
