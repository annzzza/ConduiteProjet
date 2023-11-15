package com.example.conduiteprojet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Assistance {
    public Assistance() {
    }

    public enum Status {
        OPEN, CLOSE
    }

    public enum Type {
        REQUEST, OFFER
    }
    private int id;
    private int creatorId;
    private String title;
    private String description;
    private Date createdAt;
    private Date dueDate;
    private Status status;
    private Type type;
    private boolean isCancelled;

    static Connection con = Database.getDBConnection();


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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public String getCreatorName() throws SQLException {
        String query = "SELECT firstname FROM user WHERE id_user = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, this.getCreatorId());
        ResultSet rs = ps.executeQuery();

        return rs.getString("firstname");
    }
}
