package com.example.conduiteprojet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssistanceDaoImplementation
    implements AssistanceDAO {

    static final String table="assistance";

    static Connection con = Database.getDBConnection();

    /**
     * @param assistance 
     * @throws SQLException
     */
    @Override
    public void add(Assistance assistance) throws SQLException {
        String query = "INSERT INTO " + table +
                "(title, creator, description, createdAt, dueDate, status, type, isCancelled) "
                +"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        int n = ps.executeUpdate();
    }

    /**
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + table + "WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * @param id 
     * @return
     * @throws SQLException
     */
    @Override
    public Assistance getAssistance(int id) throws SQLException {
        String query = "SELECT * FROM " + table + " WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        Assistance ass = new Assistance();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while(rs.next()) {
            check = true;
            ass.setId(rs.getInt("id"));
            ass.setTitle(rs.getString("title"));
            ass.setCreatorId(rs.getInt("creator"));
            ass.setDescription(rs.getString("description"));
            ass.setStatus(Assistance.Status.valueOf(rs.getString("status").toUpperCase()));
            ass.setType(Assistance.Type.valueOf(rs.getString("type").toUpperCase()));
            ass.setCancelled(rs.getBoolean("isCancelled"));
            ass.setDueDate(rs.getDate("dueDate"));
            ass.setCreatedAt(rs.getDate("createdAt"));
        }

        if (check) {
            return ass;
        } else {
            return null;
        }
    }

    /**
     * @return 
     * @throws SQLException
     */
    @Override
    public List<Assistance> getAssistances() throws SQLException {

        String query = "SELECT * from " + table;
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Assistance> assistances = new ArrayList<>();

        while(rs.next()) {
            Assistance ass = new Assistance();
            ass.setId(rs.getInt("id"));
            ass.setTitle(rs.getString("title"));
            ass.setCreatorId(rs.getInt("creator"));
            ass.setDescription(rs.getString("description"));
            ass.setStatus(Assistance.Status.valueOf(rs.getString("status").toUpperCase()));
            ass.setType(Assistance.Type.valueOf(rs.getString("type").toUpperCase()));
            ass.setCancelled(rs.getBoolean("isCancelled"));
            ass.setDueDate(rs.getDate("dueDate"));
            ass.setCreatedAt(rs.getDate("createdAt"));
            assistances.add(ass);
        }

        return assistances;
    }


    public List<Assistance> getAssistanceOffers() throws SQLException{

        String query = "SELECT * from " + table + " WHERE type = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, "offer");
        ResultSet rs = ps.executeQuery();
        List<Assistance> offers = new ArrayList<>();

        while(rs.next()) {
            Assistance ass = new Assistance();
            ass.setId(rs.getInt("id"));
            ass.setTitle(rs.getString("title"));
            ass.setCreatorId(rs.getInt("creator"));
            ass.setDescription(rs.getString("description"));
            ass.setStatus(Assistance.Status.valueOf(rs.getString("status").toUpperCase()));
            ass.setType(Assistance.Type.valueOf(rs.getString("type").toUpperCase()));
            ass.setCancelled(rs.getBoolean("isCancelled"));
            ass.setDueDate(rs.getDate("dueDate"));
            ass.setCreatedAt(rs.getDate("createdAt"));
            offers.add(ass);
        }

        return offers;
    }


    public List<Assistance> getAssistanceRequests() throws SQLException{

        String query = "SELECT * from " + table + " WHERE creator = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, PreferencesManager.getUserIDString());
        ResultSet rs = ps.executeQuery();
        List<Assistance> requests = new ArrayList<>();

        while(rs.next()) {
            Assistance ass = new Assistance();
            ass.setId(rs.getInt("id"));
            ass.setTitle(rs.getString("title"));
            ass.setCreatorId(rs.getInt("creator"));
            ass.setDescription(rs.getString("description"));
            ass.setStatus(Assistance.Status.valueOf(rs.getString("status").toUpperCase()));
            ass.setType(Assistance.Type.valueOf(rs.getString("type").toUpperCase()));
            ass.setCancelled(rs.getBoolean("isCancelled"));
            ass.setDueDate(rs.getDate("dueDate"));
            ass.setCreatedAt(rs.getDate("createdAt"));
            requests.add(ass);
        }

        return requests;
    }


    /**
     * @param assistance 
     * @throws SQLException
     */
    @Override
    public void update(Assistance assistance) throws SQLException {
//      "(title, creator, description, createdAt, dueDate, status, isCancelled) "
        String query = "UPDATE " + table + "SET"
                + "title=?, creator=?, description=?, createdAt=?, dueDate=?, status=?, type=?, isCancelled=?"
                + "WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        ps.executeUpdate();
    }
}
