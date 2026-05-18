package Model.communication;

import Model.research.ResearchPaper;
import java.io.*;
import java.util.*;

public class Journal implements Observable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<Observer> subscribers;
    private List<ResearchPaper> papers;

    public Journal(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Journal name cannot be empty");
        }

        this.name = name;
        this.subscribers = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    @Override
    public void subscribe(Observer observer) {
        if (observer == null) return;

        if (!subscribers.contains(observer)) {
            subscribers.add(observer);
        }
    }

    @Override
    public void unsubscribe(Observer observer) {
        if (observer == null) return;
        subscribers.remove(observer);
    }

    @Override
    public void notifySubscribers(Object update) {
        for (Observer o : subscribers) {
            o.update(update);
        }
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null");
        }

        papers.add(paper);
        notifySubscribers(paper);
    }

    public String getName() {
        return name;
    }

    public List<ResearchPaper> getPapers() {
        return new ArrayList<>(papers);
    }

    @Override
    public String toString() {
        return "Journal: " + name + ", papers: " + papers.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Journal)) return false;
        Journal journal = (Journal) o;
        return name.equals(journal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

