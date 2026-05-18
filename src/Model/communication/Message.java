package Model.communication;

import Model.users.User;

import java.io.*;
import java.util.*;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;


    private String text;
    private User sender;
    private User receiver;
    private Date date;

    public Message(String text, User sender, User receiver) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty");
        }
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver cannot be null");
        }
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same");
        }

        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return sender + " - " + receiver + ": " + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message m = (Message) o;
        return text.equals(m.text) &&
               sender.equals(m.sender) &&
               receiver.equals(m.receiver) &&
               date.equals(m.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, sender, receiver, date);
    }
}

