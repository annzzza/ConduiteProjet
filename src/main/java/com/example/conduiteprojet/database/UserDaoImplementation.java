package com.example.conduiteprojet.database;

import com.example.conduiteprojet.auth.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDaoImplementation implements UserDAO {

    static Connection con = Database.getDBConnection();
    static String table = "user";
    /**
     * @param user
     * @throws SQLException
     */
    @Override
    public void add(User user) throws SQLException {

        String query = "INSERT INTO " + table + "(firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRole());
        int i = ps.executeUpdate();

     }

    /**
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + table + " WHERE id_user =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * @param id
     * @return User
     * @throws SQLException
     */
    @Override
    public User getUser(int id) throws SQLException {
        String query = "SELECT * FROM " + table + " WHERE id_user =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        User user = new User();
        ResultSet res = ps.executeQuery();
        boolean check = false;
        while (res.next()){
            check = true;
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setRole(res.getString("role"));
        }
        if (check){
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUser(String username) throws SQLException {
        String query = "SELECT * FROM " + table + " WHERE username =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, username);
        User user = new User();
        ResultSet res = ps.executeQuery();
        boolean check = false;
        System.out.println(res);
        while (res.next()){
            check = true;
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setRole(res.getString("role"));
        }
        if (check){
            return user;
        } else {
            return null;
        }
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<User> getAllUsers() throws SQLException {

        String query = "SELECT * FROM " + table;
        PreparedStatement ps = con.prepareStatement(query);
        ArrayList<User> ls = new ArrayList<>();
        ResultSet res = ps.executeQuery();

        while (res.next()){
            User user = new User();
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setRole(res.getString("role"));
            ls.add(user);
        }
        return ls;
    }

    /**
     * @param user
     * @throws SQLException
     */
    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE " + table + " SET firstname = ?, lastname = ?, username = ?, password=?, role = ? WHERE id_user = ?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRole());
        ps.executeUpdate();
    }
}
