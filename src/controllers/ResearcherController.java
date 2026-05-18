package controllers;

import Model.communication.Journal;
import Model.enums.CitationFormat;
import Model.exceptions.NotResearcherException;
import Model.research.ResearchPaper;
import Model.research.ResearchProject;
import Model.research.Researcher;
import Model.service.ResearchService;
import Model.users.User;
import views.ResearcherView;

import java.util.Comparator;
import java.util.List;

public class ResearcherController {

    private final ResearchService service;
    private final ResearcherView view;

    public ResearcherController(ResearchService service, ResearcherView view) {
        this.service = service;
        this.view = view;
    }

    public void openMenu(User user) {
        if (!(user instanceof Researcher)) {
            view.showError("Not a researcher!");
            return;
        }

        Researcher researcher = (Researcher) user;
        boolean running = true;

        while (running) {
            try {
                switch (view.showMenu()) {
                    case 1 -> view.showPapers(researcher.getResearchPapers());
                    case 2 -> addPaper(researcher);
                    case 3 -> view.showInfo("h-index", String.valueOf(service.calculateHIndex(researcher)));
                    case 4 -> printSortedPapers(researcher);
                    case 5 -> printCitation(researcher);
                    case 6 -> view.showProjects(researcher.getResearchProjects());
                    case 7 -> joinProject(researcher);
                    case 8 -> subscribeToJournal(user);
                    case 9 -> running = false;
                    default -> view.showError("Unknown option");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                view.showError(e.getMessage());
            }
        }
    }

    private void addPaper(Researcher researcher) {
        ResearchPaper paper = view.readPaper();
        service.addPaper(researcher, paper);
        view.showSuccess("Paper added successfully!");
    }

    private void printSortedPapers(Researcher researcher) {
        int choice = view.readSortOption();
        Comparator<ResearchPaper> comparator = switch (choice) {
            case 1 -> Comparator.naturalOrder();
            case 2 -> ResearchPaper::compareByCitations;
            case 3 -> ResearchPaper::compareByPages;
            case 4 -> null;
            default -> throw new IllegalArgumentException("Unknown sorting option");
        };

        if (comparator != null) {
            service.printPapers(researcher, comparator);
        }
    }

    private void printCitation(Researcher researcher) {
        List<ResearchPaper> papers = researcher.getResearchPapers();
        view.showPapers(papers);
        int index = view.readPaperIndex();

        if (index < 0 || index >= papers.size()) {
            throw new IllegalArgumentException("Paper not found");
        }

        CitationFormat format = view.readCitationFormat();
        view.showInfo("Citation", service.getCitation(papers.get(index), format));
    }

    private void joinProject(Researcher researcher) {
        List<ResearchProject> projects = service.getAllProjects();
        view.showProjects(projects);

        int index = view.readProjectIndex();
        if (index < 0 || index >= projects.size()) {
            throw new IllegalArgumentException("Project not found");
        }

        try {
            projects.get(index).addParticipant(researcher);
            researcher.joinProject(projects.get(index));
            view.showSuccess("Joined project successfully!");
        } catch (NotResearcherException e) {
            view.showError(e.getMessage());
        }
    }

    private void subscribeToJournal(User user) {
        Journal journal = view.chooseJournal(service.getJournals());
        service.subscribeToJournal(user, journal);
        view.showSuccess("Subscribed to journal: " + journal.getName());
    }
}

