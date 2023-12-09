package com.example.conduiteprojet.database;

import com.example.conduiteprojet.auth.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.example.conduiteprojet.database.Database.getDBConnection;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplementationTest {

    UserDaoImplementation udi = new UserDaoImplementation();
    Connection con = getDBConnection();
    String usernameTest = "DaoUsernameTest";

    @Test
    void add() throws SQLException {
        User user = new User();
        user.setFirstName("DaoFirstName");
        user.setLastName("DaoLastName");
        user.setUsername(usernameTest);
        user.setPassword("DaoPassword");
        user.setRole(User.Role.PATIENT);

        udi.add(user);

        // Check that user is in the database
        String query = "SELECT * FROM user WHERE username =?";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, usernameTest);
        java.sql.ResultSet res = ps.executeQuery();
        boolean check = false;
        while (res.next()) {
            check = true;
            assertEquals(user.getFirstName(), res.getString("firstname"));
            assertEquals(user.getLastName(), res.getString("lastname"));
            assertEquals(user.getUsername(), res.getString("username"));
            assertEquals(user.getPassword(), res.getString("password"));
            assertEquals(user.getRole().toString(), res.getString("role"));
        }
        assertTrue(check);

        // Delete user with SQL and username
        String queryDelete = "DELETE FROM user WHERE username =?";
        java.sql.PreparedStatement psDelete = con.prepareStatement(queryDelete);
        psDelete.setString(1, usernameTest);
        psDelete.executeUpdate();
    }

    @Test
    void delete() throws SQLException {
        // Create user
        User user = new User();
        user.setFirstName("DaoFirstName");
        user.setLastName("DaoLastName");
        user.setUsername(usernameTest);
        user.setPassword("DaoPassword");
        user.setRole(User.Role.PATIENT);

        // Add user with SQL
        String queryAdd = "INSERT INTO user (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement psAdd = con.prepareStatement(queryAdd);
        psAdd.setString(1, user.getFirstName());
        psAdd.setString(2, user.getLastName());
        psAdd.setString(3, user.getUsername());
        psAdd.setString(4, user.getPassword());
        psAdd.setString(5, user.getRole().toString());
        psAdd.executeUpdate();

        // Update user to get its id
        String queryUpdate = "SELECT * FROM user WHERE username =?";
        java.sql.PreparedStatement psUpdate = con.prepareStatement(queryUpdate);
        psUpdate.setString(1, usernameTest);
        java.sql.ResultSet resUpdate = psUpdate.executeQuery();
        while (resUpdate.next()) {
            user.setId(resUpdate.getInt("id_user"));
        }

        // Delete user
        udi.delete(user.getId());

        // Check that user is not in the database
        String query = "SELECT * FROM user WHERE id_user =?";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, user.getId());
        java.sql.ResultSet res = ps.executeQuery();
        boolean check = false;
        while (res.next()) {
            check = true;
        }
        assertFalse(check);
    }

    @Test
    void getUserId() throws SQLException {
        // Create user
        User user = new User();
        user.setFirstName("DaoFirstName");
        user.setLastName("DaoLastName");
        user.setUsername(usernameTest);
        user.setPassword("DaoPassword");
        user.setRole(User.Role.PATIENT);

        // Add user with SQL
        String queryAdd = "INSERT INTO user (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement psAdd = con.prepareStatement(queryAdd);
        psAdd.setString(1, user.getFirstName());
        psAdd.setString(2, user.getLastName());
        psAdd.setString(3, user.getUsername());
        psAdd.setString(4, user.getPassword());
        psAdd.setString(5, user.getRole().toString());
        psAdd.executeUpdate();

        // Update user to get its id
        String queryUpdate = "SELECT * FROM user WHERE username =?";
        java.sql.PreparedStatement psUpdate = con.prepareStatement(queryUpdate);
        psUpdate.setString(1, usernameTest);
        java.sql.ResultSet resUpdate = psUpdate.executeQuery();
        while (resUpdate.next()) {
            user.setId(resUpdate.getInt("id_user"));
        }

        // Check that user is in the database
        User userFromDao = udi.getUser(user.getId());
        assertEquals(user.getFirstName(), userFromDao.getFirstName());
        assertEquals(user.getLastName(), userFromDao.getLastName());
        assertEquals(user.getUsername(), userFromDao.getUsername());
        assertEquals(user.getPassword(), userFromDao.getPassword());
        assertEquals(user.getRole(), userFromDao.getRole());

        // Delete user with SQL and username
        String queryDelete = "DELETE FROM user WHERE username =?";
        java.sql.PreparedStatement psDelete = con.prepareStatement(queryDelete);
        psDelete.setString(1, usernameTest);
        psDelete.executeUpdate();
    }

    @Test
    void testGetUserString() throws SQLException {
        // Create user
        User user = new User();
        user.setFirstName("DaoFirstName");
        user.setLastName("DaoLastName");
        user.setUsername(usernameTest);
        user.setPassword("DaoPassword");
        user.setRole(User.Role.PATIENT);

        // Add user with SQL
        String queryAdd = "INSERT INTO user (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement psAdd = con.prepareStatement(queryAdd);
        psAdd.setString(1, user.getFirstName());
        psAdd.setString(2, user.getLastName());
        psAdd.setString(3, user.getUsername());
        psAdd.setString(4, user.getPassword());
        psAdd.setString(5, user.getRole().toString());
        psAdd.executeUpdate();

        // Check that user is in the database
        User userFromDao = udi.getUser(usernameTest);
        assertEquals(user.getFirstName(), userFromDao.getFirstName());
        assertEquals(user.getLastName(), userFromDao.getLastName());
        assertEquals(user.getUsername(), userFromDao.getUsername());
        assertEquals(user.getPassword(), userFromDao.getPassword());
        assertEquals(user.getRole(), userFromDao.getRole());

        // Delete user with SQL and username
        String queryDelete = "DELETE FROM user WHERE username =?";
        java.sql.PreparedStatement psDelete = con.prepareStatement(queryDelete);
        psDelete.setString(1, usernameTest);
        psDelete.executeUpdate();
    }

    @Test
    void getAllUsers() throws SQLException {
        // Create some users
        User user1 = new User();
        user1.setFirstName("DaoFirstName1");
        user1.setLastName("DaoLastName1");
        user1.setUsername(usernameTest + "1");
        user1.setPassword("DaoPassword1");
        user1.setRole(User.Role.PATIENT);

        User user2 = new User();
        user2.setFirstName("DaoFirstName2");
        user2.setLastName("DaoLastName2");
        user2.setUsername(usernameTest + "2");
        user2.setPassword("DaoPassword2");
        user2.setRole(User.Role.PATIENT);

        // Add users with SQL
        String queryAdd = "INSERT INTO user (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement psAdd = con.prepareStatement(queryAdd);
        psAdd.setString(1, user1.getFirstName());
        psAdd.setString(2, user1.getLastName());
        psAdd.setString(3, user1.getUsername());
        psAdd.setString(4, user1.getPassword());
        psAdd.setString(5, user1.getRole().toString());
        psAdd.executeUpdate();

        psAdd.setString(1, user2.getFirstName());
        psAdd.setString(2, user2.getLastName());
        psAdd.setString(3, user2.getUsername());
        psAdd.setString(4, user2.getPassword());
        psAdd.setString(5, user2.getRole().toString());
        psAdd.executeUpdate();

        // Get all users
        List<User> users = udi.getAllUsers();

        // Check that users are in the database
        boolean check1 = false;
        boolean check2 = false;
        for (User user : users) {
            if (user.getUsername().equals(user1.getUsername())
                    && user.getFirstName().equals(user1.getFirstName())
                    && user.getLastName().equals(user1.getLastName())
                    && user.getPassword().equals(user1.getPassword())
                    && user.getRole().equals(user1.getRole())) {
                check1 = true;
            }
            if (user.getUsername().equals(user2.getUsername()) &&
                    user.getFirstName().equals(user2.getFirstName()) &&
                    user.getLastName().equals(user2.getLastName()) &&
                    user.getPassword().equals(user2.getPassword()) &&
                    user.getRole().equals(user2.getRole())) {
                check2 = true;
            }
        }
        assertTrue(check1);
        assertTrue(check2);


        // Delete users with SQL and username
        String queryDelete = "DELETE FROM user WHERE username =?";
        java.sql.PreparedStatement psDelete = con.prepareStatement(queryDelete);
        psDelete.setString(1, user1.getUsername());
        psDelete.executeUpdate();
        psDelete.setString(1, user2.getUsername());
        psDelete.executeUpdate();

    }

    @Test
    void update() throws SQLException {
        // Create user
        User user = new User();
        user.setFirstName("DaoFirstName");
        user.setLastName("DaoLastName");
        user.setUsername(usernameTest);
        user.setPassword("DaoPassword");
        user.setRole(User.Role.PATIENT);

        // Add user with SQL
        String queryAdd = "INSERT INTO user (firstname, lastname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement psAdd = con.prepareStatement(queryAdd);
        psAdd.setString(1, user.getFirstName());
        psAdd.setString(2, user.getLastName());
        psAdd.setString(3, user.getUsername());
        psAdd.setString(4, user.getPassword());
        psAdd.setString(5, user.getRole().toString());
        psAdd.executeUpdate();

        // Update user to get its id
        String queryUpdate = "SELECT * FROM user WHERE username =?";
        java.sql.PreparedStatement psUpdate = con.prepareStatement(queryUpdate);
        psUpdate.setString(1, usernameTest);
        java.sql.ResultSet resUpdate = psUpdate.executeQuery();
        while (resUpdate.next()) {
            user.setId(resUpdate.getInt("id_user"));
        }

        // Update user
        user.setFirstName("DaoFirstNameUpdated");
        user.setLastName("DaoLastNameUpdated");
        user.setUsername(usernameTest + "Updated");
        user.setPassword("DaoPasswordUpdated");
        user.setRole(User.Role.VALIDEUR);
        udi.update(user);

        // Check that user is in the database
        String query = "SELECT * FROM user WHERE id_user =?";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, user.getId());
        java.sql.ResultSet res = ps.executeQuery();
        boolean check = false;
        while (res.next()) {
            check = true;
            assertEquals(user.getFirstName(), res.getString("firstname"));
            assertEquals(user.getLastName(), res.getString("lastname"));
            assertEquals(user.getUsername(), res.getString("username"));
            assertEquals(user.getPassword(), res.getString("password"));
            assertEquals(user.getRole().toString(), res.getString("role"));
        }
        assertTrue(check);

        // Delete user with SQL and username
        String queryDelete = "DELETE FROM user WHERE username =?";
        java.sql.PreparedStatement psDelete = con.prepareStatement(queryDelete);
        psDelete.setString(1, usernameTest + "Updated");
        psDelete.executeUpdate();

    }
}