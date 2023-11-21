package com.example.conduiteprojet;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {

    public void add(User user) throws SQLException;

    public void delete(int id) throws SQLException;

    public User getUser(int id) throws SQLException;

    public User getUser(String username) throws SQLException;

    public ArrayList<User> getAllUsers() throws SQLException;

    public void update(User user) throws SQLException;

}
