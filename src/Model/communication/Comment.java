package Model.communication;

import Model.users.User;

import java.io.*;
import java.util.*;

public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;
    private News news;
    private User author;
    private Date date;

    public Comment(String text, User author, News news) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        if (author == null || news == null) {
            throw new IllegalArgumentException("Author and News cannot be null");
        }

        this.text = text;
        this.author = author;
        this.news = news;
        this.date = new Date();

       
        news.addComment(this);
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public News getNews() {
        return news;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return author + ": " + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return text.equals(comment.text) &&
               author.equals(comment.author) &&
               date.equals(comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, author, date);
    }
}

