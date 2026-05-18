package Model.research;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

import Model.enums.CitationFormat;

public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private List<String> authors;
    private String journal;
    private int pages;
    private int citations;
    private Date publicationDate;
    private String doi;

    public ResearchPaper() {
    }

    public ResearchPaper(String title, List<String> authors, String journal,
                         int pages, int citations, Date publicationDate, String doi) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.citations = citations;
        this.publicationDate = publicationDate;
        this.doi = doi;
    }

    public String getCitation(String format) {
        if (format == null) {
            return title + ", " + journal;
        }

        if (format.equalsIgnoreCase("BIBTEX")) {
            return "@article{" + doi + ", title={" + title + "}, journal={" +
                    journal + "}, year={" + publicationDate + "}}";
        }

        return title + ". " + journal + ". DOI: " + doi;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.BIBTEX) {
            return getCitation("BIBTEX");
        }
        return getCitation("PLAIN_TEXT");
    }

    public String getAuthorsString() {
        return String.join(", ", authors);
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return this.publicationDate.compareTo(other.publicationDate);
    }

    public static int compareByCitations(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.citations, a.citations);
    }

    public static int compareByPages(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.pages, a.pages);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public int getCitations() {
        return citations;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getDoi() {
        return doi;
    }

    @Override
    public String toString() {
        return title + " | " + journal + " | citations: " + citations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }
}

