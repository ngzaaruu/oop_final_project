package Model.service;

import Model.users.User;
import Model.academic.Course;
import Model.communication.News;
import Model.communication.Journal;
import Model.communication.OfficialMessage;
import Model.research.ResearchPaper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {

    private static final long serialVersionUID = 1L;
    private static DataStorage instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private List<Journal> journals = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();
    private List<OfficialMessage> officialMessages = new ArrayList<>();
    private List<String> systemLogs = new ArrayList<>();

    private DataStorage() {}

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void addUser(User user) {
        ensureLists();
        if (user != null && !users.contains(user)) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public boolean removeUserById(int id) {
        ensureLists();
        User user = findUserById(String.valueOf(id));
        if (user == null) {
            return false;
        }
        users.remove(user);
        return true;
    }

    public boolean updateUser(User updatedUser) {
        ensureLists();
        if (updatedUser == null) {
            return false;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    public List<User> getUsers() {
        ensureLists();
        return new ArrayList<>(users);
    }

    public User findUserById(String id) {
        ensureLists();
        if (id == null) return null;
        for (User user : users) {
            if (String.valueOf(user.getId()).equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        ensureLists();
        if (email == null) return null;
        for (User user : users) {
            if (email.equalsIgnoreCase(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public void addCourse(Course c) {
        if (c != null && !courses.contains(c)) {
            courses.add(c);
        }
    }

    public void removeCourse(Course c) {
        courses.remove(c);
    }

    public List<Course> getCourses() { return courses; }

    public void addNews(News n) {
        if (n != null && !newsList.contains(n)) {
            newsList.add(n);
            newsList.sort((a, b) -> Boolean.compare(b.isPinned(), a.isPinned()));
        }
    }

    public List<News> getNews() { return newsList; }

    public void addJournal(Journal j) {
        if (j != null && !journals.contains(j)) {
            journals.add(j);
        }
    }

    public List<Journal> getJournals() { return journals; }

    public void addPaper(ResearchPaper p) {
        if (p != null && !papers.contains(p)) {
            papers.add(p);
        }
    }

    public List<ResearchPaper> getPapers() { return papers; }

    public void addOfficialMessage(OfficialMessage message) {
        if (message != null && !officialMessages.contains(message)) {
            officialMessages.add(message);
        }
    }

    public List<OfficialMessage> getOfficialMessages() {
        return officialMessages;
    }

    public void addSystemLog(String log) {
        ensureLists();
        if (log != null && !log.isEmpty()) {
            systemLogs.add(log);
        }
    }

    public List<String> getSystemLogs() {
        ensureLists();
        return new ArrayList<>(systemLogs);
    }

    private void ensureLists() {
        if (users == null) users = new ArrayList<>();
        if (courses == null) courses = new ArrayList<>();
        if (newsList == null) newsList = new ArrayList<>();
        if (journals == null) journals = new ArrayList<>();
        if (papers == null) papers = new ArrayList<>();
        if (officialMessages == null) officialMessages = new ArrayList<>();
        if (systemLogs == null) systemLogs = new ArrayList<>();
    }

    public void save(String filename) {
        ensureLists();
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save data storage", e);
        }
    }

    public static DataStorage load(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            instance = (DataStorage) in.readObject();
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not load data storage", e);
        }
    }
}

