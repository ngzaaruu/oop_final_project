package views;

import Model.communication.Journal;
import Model.enums.CitationFormat;
import Model.research.ResearchPaper;
import Model.research.ResearchProject;
import core.utils.ConsolePrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ResearcherView {

    private final Scanner scanner;

    public ResearcherView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int showMenu() {
        ConsolePrinter.printMenu("RESEARCHER MENU", new String[]{
                "View my papers",
                "Add paper",
                "View h-index",
                "Print papers sorted by date / citations / pages",
                "Get citation (Plain Text / BibTeX)",
                "View research projects",
                "Join research project",
                "Subscribe to journal",
                "Back"
        });
        return readInt("Choose: ");
    }

    public void showPapers(List<ResearchPaper> papers) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < papers.size(); i++) {
            ResearchPaper paper = papers.get(i);
            rows.add(Arrays.asList(
                    String.valueOf(i + 1),
                    paper.getTitle(),
                    paper.getJournal(),
                    String.valueOf(paper.getCitations()),
                    String.valueOf(paper.getPages())
            ));
        }
        ConsolePrinter.printTable(Arrays.asList("#", "Title", "Journal", "Citations", "Pages"), rows);
    }

    public ResearchPaper readPaper() {
        String title = readLine("Title: ");
        String authorsText = readLine("Authors (comma separated): ");
        String journal = readLine("Journal: ");
        int pages = readInt("Pages: ");
        int citations = readInt("Citations: ");
        String doi = readLine("DOI: ");

        List<String> authors = new ArrayList<>();
        for (String author : authorsText.split(",")) {
            if (!author.trim().isEmpty()) {
                authors.add(author.trim());
            }
        }

        return new ResearchPaper(title, authors, journal, pages, citations, new Date(), doi);
    }

    public int readSortOption() {
        ConsolePrinter.printMenu("SORT PAPERS", new String[]{"Date", "Citations", "Pages", "Back"});
        return readInt("Choose: ");
    }

    public CitationFormat readCitationFormat() {
        ConsolePrinter.printMenu("CITATION FORMAT", new String[]{"Plain Text", "BibTeX"});
        int choice = readInt("Choose: ");
        return choice == 2 ? CitationFormat.BIBTEX : CitationFormat.PLAIN_TEXT;
    }

    public int readPaperIndex() {
        return readInt("Paper number: ") - 1;
    }

    public void showProjects(List<ResearchProject> projects) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            ResearchProject project = projects.get(i);
            rows.add(Arrays.asList(
                    String.valueOf(i + 1),
                    project.getProjectId(),
                    project.getTopic(),
                    String.valueOf(project.getParticipants().size())
            ));
        }
        ConsolePrinter.printTable(Arrays.asList("#", "ID", "Topic", "Participants"), rows);
    }

    public int readProjectIndex() {
        return readInt("Project number: ") - 1;
    }

    public Journal chooseJournal(List<Journal> journals) {
        if (!journals.isEmpty()) {
            List<List<String>> rows = new ArrayList<>();
            for (int i = 0; i < journals.size(); i++) {
                rows.add(Arrays.asList(String.valueOf(i + 1), journals.get(i).getName()));
            }
            ConsolePrinter.printTable(Arrays.asList("#", "Journal"), rows);
        }

        String value = readLine("Journal number or new journal name: ");
        try {
            int index = Integer.parseInt(value) - 1;
            if (index >= 0 && index < journals.size()) {
                return journals.get(index);
            }
        } catch (NumberFormatException ignored) {
        }

        return new Journal(value);
    }

    public void showSuccess(String message) {
        ConsolePrinter.printSuccess(message);
    }

    public void showError(String message) {
        ConsolePrinter.printError(message);
    }

    public void showInfo(String label, String value) {
        ConsolePrinter.printInfo(label, value);
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                ConsolePrinter.printError("Enter a valid number");
            }
        }
    }
}

