package Model.service;

import Model.communication.Journal;
import Model.enums.CitationFormat;
import Model.research.ResearchPaper;
import Model.research.ResearchProject;
import Model.research.Researcher;
import Model.users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResearchService {

    private final DataStorage storage;
    private final List<ResearchProject> projects;

    public ResearchService() {
        this.storage = DataStorage.getInstance();
        this.projects = new ArrayList<>();
    }

    public void addPaper(Researcher researcher, ResearchPaper paper) {
        if (researcher == null || paper == null) {
            throw new IllegalArgumentException("Researcher and paper cannot be null");
        }

        researcher.addResearchPaper(paper);
        storage.addPaper(paper);
    }

    public int calculateHIndex(Researcher researcher) {
        if (researcher == null) {
            throw new IllegalArgumentException("Researcher cannot be null");
        }
        return researcher.calculateHIndex();
    }

    public void printPapers(Researcher researcher, Comparator<ResearchPaper> comparator) {
        if (researcher == null || comparator == null) {
            throw new IllegalArgumentException("Researcher and comparator cannot be null");
        }
        researcher.printPapers(comparator);
    }

    public String getCitation(ResearchPaper paper, CitationFormat format) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null");
        }
        return paper.getCitation(format);
    }

    public List<ResearchProject> getAllProjects() {
        return new ArrayList<>(projects);
    }

    public void addProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
        }
    }

    public List<Journal> getJournals() {
        return storage.getJournals();
    }

    public void subscribeToJournal(User user, Journal journal) {
        if (user == null || journal == null) {
            throw new IllegalArgumentException("User and journal cannot be null");
        }

        storage.addJournal(journal);
        journal.subscribe(user);
    }
}

