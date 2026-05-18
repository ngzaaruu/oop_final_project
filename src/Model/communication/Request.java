package Model.communication;

import Model.users.User;
import Model.enums.RequestStatus;

import java.io.*;
import java.util.*;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private String description;
    private User sender;
    private RequestStatus status;   
    private Date createdAt;
    private User signedBy;
    private Date signedAt;

    public Request(String description, User sender) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }

        this.description = description;
        this.sender = sender;
        this.status = RequestStatus.NEW;
        this.createdAt = new Date();

        sender.addRequest(this);
    }

    public void markViewed() {
        status = RequestStatus.VIEWED;
    }

    public void accept() {
        status = RequestStatus.ACCEPTED;
    }

    public void reject() {
        status = RequestStatus.REJECTED;
    }

    public void complete() {
        status = RequestStatus.DONE;
    }

    public String getDescription() {
        return description;
    }

    public User getSender() {
        return sender;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public void sign(User signer) {
        if (signer == null) {
            throw new IllegalArgumentException("Signer cannot be null");
        }
        this.signedBy = signer;
        this.signedAt = new Date();
    }

    public boolean isSigned() {
        return signedBy != null;
    }

    public User getSignedBy() {
        return signedBy;
    }

    public Date getSignedAt() {
        return signedAt == null ? null : new Date(signedAt.getTime());
    }

    @Override
    public String toString() {
        return "Request from " + sender + ": " + description + " (" + status + ")" +
                (isSigned() ? " signed by " + signedBy.getFullName() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return description.equals(request.description) &&
                sender.equals(request.sender) &&
                createdAt.equals(request.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, sender, createdAt);
    }
}

