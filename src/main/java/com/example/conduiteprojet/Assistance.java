package com.example.conduiteprojet;

import java.sql.Date;

/**
 *
 */
public class Assistance {
    private int id;
    private User creator;
    private String description;
    private Date createdAt;
    private Date dueDate;

    private String status;

    private boolean isCancelled;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    public boolean isCancelled() {
        return isCancelled;
    }
}
