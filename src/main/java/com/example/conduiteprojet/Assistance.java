package com.example.conduiteprojet;

import java.sql.Date;

/**
 *
 */
public class Assistance {
    public enum Status {
        OPEN, CLOSE
    }
    private int id;
    private int creatorId;
    private String title;
    private String description;
    private Date createdAt;
    private Date dueDate;
    private Status status;
    private boolean isCancelled;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    public boolean isCancelled() {
        return isCancelled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
