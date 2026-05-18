package Model.communication;

import Model.users.User;
import Model.enums.NewsType;

import java.io.*;
import java.util.*;

public class News implements Serializable {

    private static final long serialVersionUID = 1L;

   
    private String title;
    private String content;
    private NewsType topic;
    private boolean isPinned;
    private User author;
    private Date date;
    private List<Comment> comments;

    public News(String title, String content, NewsType topic, User author) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        if (topic == null || author == null) {
            throw new IllegalArgumentException("Topic and author cannot be null");
        }

        this.title = title;
        this.content = content;
        this.topic = topic;
        this.author = author;
        this.date = new Date();
        this.comments = new ArrayList<>();

        this.isPinned = (topic == NewsType.RESEARCH);
    }

    public void addComment(Comment c) {
        if (c == null) return;

        if (!comments.contains(c)) {
            comments.add(c);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NewsType getTopic() {
        return topic;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public User getAuthor() {
        return author;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public String toString() {
        return (isPinned ? "[PINNED] " : "") + title + " - " + author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return title.equals(news.title) &&
                author.equals(news.author) &&
                date.equals(news.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, date);
    }
}

