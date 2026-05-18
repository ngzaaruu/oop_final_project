package Model.communication;

import Model.users.User;

import java.io.Serializable;
import java.util.Date;

public class OfficialMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String details;
    private User createdBy;
    private Date createdAt;

    public OfficialMessage(String title, String details, User createdBy) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("Details cannot be empty");
        }
        if (createdBy == null) {
            throw new IllegalArgumentException("Creator cannot be null");
        }

        this.title = title;
        this.details = details;
        this.createdBy = createdBy;
        this.createdAt = new Date();
    }

    public static OfficialMessage roomBooking(User createdBy, String room, Date date, String purpose) {
        if (room == null || room.isEmpty()) {
            throw new IllegalArgumentException("Room cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return new OfficialMessage(
                "Room booking",
                "Room " + room + " booked for " + purpose + " on " + date,
                createdBy
        );
    }

    public static OfficialMessage examPlanned(User createdBy, String courseName, Date date, String room) {
        if (courseName == null || courseName.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return new OfficialMessage(
                "Exam planned",
                "Exam for " + courseName + " planned on " + date + " in room " + room,
                createdBy
        );
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    @Override
    public String toString() {
        return title + ": " + details + " | created by " + createdBy.getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficialMessage)) return false;
        OfficialMessage that = (OfficialMessage) o;
        return title.equals(that.title) &&
                details.equals(that.details) &&
                createdBy.equals(that.createdBy) &&
                createdAt.equals(that.createdAt);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(title, details, createdBy, createdAt);
    }
}

