package com.example.conduiteprojet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssistanceDaoImplementation
    implements AssistanceDAO {

    static String table="assistance";

    static Connection con = Database.getDBConnection();

    /**
     * @param assistance 
     * @throws SQLException
     */
    @Override
    public void add(Assistance assistance) throws SQLException {
        String query = "INSERT INTO " + table + "(title) VALUES ( ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
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
        String query = "SELECT * FROM " + table + "WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        Assistance ass = new Assistance();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while(rs.next()) {
            check = true;
            ass.setId(rs.getInt("id"));
            ass.setTitle(rs.getString("title"));
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
            assistances.add(ass);
        }

        return assistances;
    }

    /**
     * @param assistance 
     * @throws SQLException
     */
    @Override
    public void update(Assistance assistance) throws SQLException {
        String query = "UPDATE " + table + "SET"
                + "title=?,"
                + "WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2,assistance.getId());
        ps.executeUpdate();
    }
}
