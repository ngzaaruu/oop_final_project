package Model.service;

import Model.users.*;
import Model.academic.Course;
import Model.communication.News;
import Model.communication.OfficialMessage;
import Model.enums.NewsType;
import Model.exceptions.AuthenticationException;
import Model.research.ResearchPaper;
import Model.research.Researcher;

import java.util.List;

public class UniversityService {

    private DataStorage storage;
    private User currentUser;

    public UniversityService() {
        this.storage = DataStorage.getInstance();
    }

    public User login(String email, String password) throws AuthenticationException {

        for (User u : storage.getUsers()) {
            if (u.login(email, password)) {
                currentUser = u;
                return u;
            }
        }

        throw new AuthenticationException("Invalid email or password");
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addUser(User user) {
        storage.addUser(user);
    }

    public void removeUser(User user) {
        storage.removeUser(user);
    }

    public void addCourse(Course course) {
        storage.addCourse(course);
    }

    public List<Course> getAllCourses() {
        return storage.getCourses();
    }

    public void publishNews(News news) {
        storage.addNews(news);
    }

    public News publishResearchPaper(Researcher researcher, ResearchPaper paper) {
        if (researcher == null || paper == null) {
            throw new IllegalArgumentException("Researcher and paper cannot be null");
        }
        if (!(researcher instanceof User)) {
            throw new IllegalArgumentException("Researcher must be a university user to publish news");
        }

        researcher.publishPaper(paper);
        storage.addPaper(paper);

        User author = (User) researcher;
        News news = new News(
                "New research paper published",
                author.getFullName() + " published \"" + paper.getTitle() + "\"",
                NewsType.RESEARCH,
                author
        );
        publishNews(news);
        return news;
    }

    public News generateTopCitedResearcherNews(List<Researcher> researchers) {
        ReportService reportService = new ReportService();
        Researcher topResearcher = reportService.findTopCitedResearcher(researchers);

        if (topResearcher == null) {
            throw new IllegalArgumentException("Researchers list is empty");
        }
        if (!(topResearcher instanceof User)) {
            throw new IllegalArgumentException("Top researcher must be a university user");
        }

        User author = (User) topResearcher;
        int citations = reportService.getTotalCitations(topResearcher);
        News news = new News(
                "Top cited researcher",
                author.getFullName() + " is the top cited researcher with " + citations + " citations",
                NewsType.RESEARCH,
                author
        );
        publishNews(news);
        return news;
    }

    public List<News> getAllNews() {
        return storage.getNews();
    }

    public void addOfficialMessage(OfficialMessage message) {
        storage.addOfficialMessage(message);
    }

    public List<OfficialMessage> getOfficialMessages() {
        return storage.getOfficialMessages();
    }

    public void printAllUsers() {
        for (User u : storage.getUsers()) {
            System.out.println(u);
        }
    }
}

