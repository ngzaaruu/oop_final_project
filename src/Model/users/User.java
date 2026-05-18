package Model.users;

import Model.communication.*;
import Model.enums.Language;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import Model.communication.Observer;

public abstract class User implements Observer, Serializable {

    protected int id;
    protected String fullName;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected Language language;
    protected LocalDate registeredDate;
    protected boolean active;

    private List<Message> inbox;
    private List<Message> sentMessages;
    private List<Request> requests;

    public User(int id, String fullName, String email, String password,
                String phoneNumber, Language language) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.language = language;

        this.registeredDate = LocalDate.now();
        this.active = true;

        this.inbox = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return active &&
               this.email != null &&
               this.password != null &&
               this.email.equals(email) &&
               this.password.equals(password);
    }

    public void logout() {
        System.out.println(fullName + " logged out");
    }

    public void changeLanguage(Language language) {
        if (language != null) {
            this.language = language;
        }
    }

    public void sendMessage(User receiver, String text) {
        if (receiver == null || text == null || text.isEmpty()) return;

        Message m = new Message(text, this, receiver);
        sentMessages.add(m);
        receiver.receiveMessage(m); 
    }

    public void receiveMessage(Message message) {
        if (message != null) {
            inbox.add(message);
        }
    }

    public List<Message> getInbox() {
        return new ArrayList<>(inbox);
    }

    public List<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public void addRequest(Request request) {
        if (request != null) {
            requests.add(request);
        }
    }

    public List<Request> getRequests() {
        return new ArrayList<>(requests);
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public void update(Object update) {
        System.out.println(fullName + " received notification: " + update);
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return fullName + " (" + email + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
