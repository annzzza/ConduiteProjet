package com.example.conduiteprojet.database;

import com.example.conduiteprojet.app.Assistance;
import com.example.conduiteprojet.auth.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static com.example.conduiteprojet.database.Database.getDBConnection;
import static org.junit.jupiter.api.Assertions.*;

class AssistanceDaoImplementationTest {

    UserDaoImplementation udi = new UserDaoImplementation();
    Connection con = getDBConnection();
    User user;

    @BeforeEach
    void setUp() throws SQLException {
        // Create a user
        user = new User();
        user.setUsername("TestAssistanceCreator");
        user.setFirstName("TestAssistanceCreatorFirstName");
        user.setLastName("TestAssistanceCreatorLastName");
        user.setPassword("TestAssistanceCreatorPassword");
        user.setRole(User.Role.PATIENT);

        // Add it to the database with SQL
        String query = "INSERT INTO user (username, firstName, lastName, password, role) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRole().toString());
        ps.executeUpdate();

        // Check that it is added
        query = "SELECT * FROM user WHERE username=?";
        ps = con.prepareStatement(query);
        ps.setString(1, user.getUsername());
        java.sql.ResultSet rs = ps.executeQuery();
        assertTrue(rs.next());
        assertEquals(user.getUsername(), rs.getString("username"));
        assertEquals(user.getFirstName(), rs.getString("firstName"));
        assertEquals(user.getLastName(), rs.getString("lastName"));
        assertEquals(user.getPassword(), rs.getString("password"));
        assertEquals(user.getRole().toString(), rs.getString("role"));

        // Set the id of the user
        user.setId(rs.getInt("id_user"));
    }

    @Test
    void add() throws SQLException {

        // Create an assistance
        Assistance assistance = new Assistance();
        assistance.setTitle("TestAssistanceTitle");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.PENDING);
        assistance.setType(Assistance.Type.OFFER);
        assistance.setCancelled(false);

        // Add it to the database
        AssistanceDaoImplementation adi = new AssistanceDaoImplementation();
        adi.add(assistance);

        // Check that it is added
        String query2 = "SELECT * FROM assistance WHERE title=?";
        java.sql.PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, assistance.getTitle());
        java.sql.ResultSet rs2 = ps2.executeQuery();
        assertTrue(rs2.next());
        assertEquals(assistance.getTitle(), rs2.getString("title"));
        assertEquals(assistance.getCreatorId(), rs2.getInt("creator"));
        assertEquals(assistance.getDescription(), rs2.getString("description"));
        assertEquals(assistance.getCreatedAt().toString(), rs2.getDate("createdAt").toString());
        assertEquals(assistance.getDueDate().toString(), rs2.getDate("dueDate").toString());
        assertEquals(assistance.getStatus().toString(), rs2.getString("status"));
        assertEquals(assistance.getType().toString(), rs2.getString("type"));
        assertEquals(assistance.isCancelled(), rs2.getBoolean("isCancelled"));

        int assistanceId = rs2.getInt("id");

        // Delete with SQL and id
        String query3 = "DELETE FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps3 = con.prepareStatement(query3);
        ps3.setInt(1, assistanceId);
        ps3.executeUpdate();
    }

    @Test
    void delete() throws SQLException {
        // Create an assistance
        Assistance assistance = new Assistance();
        assistance.setTitle("TestAssistanceTitle");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.PENDING);
        assistance.setType(Assistance.Type.OFFER);
        assistance.setCancelled(false);

        // Add it to the database with SQL
        String query = "INSERT INTO assistance (title, creator, description, createdAt, dueDate, status, type, isCancelled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        ps.executeUpdate();


        // Check that it is added
        String query2 = "SELECT * FROM assistance WHERE title=?";
        java.sql.PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, assistance.getTitle());
        java.sql.ResultSet rs2 = ps2.executeQuery();
        assertTrue(rs2.next());

        // Get the id of the assistance
        int assistanceId = rs2.getInt("id");

        // Delete with DAO and id
        AssistanceDaoImplementation adi = new AssistanceDaoImplementation();
        adi.delete(assistanceId);

        // Check that it is deleted
        String query3 = "SELECT * FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps3 = con.prepareStatement(query3);
        ps3.setInt(1, assistanceId);
        java.sql.ResultSet rs3 = ps3.executeQuery();
        assertFalse(rs3.next());

    }

    @Test
    void getAssistance() throws SQLException {
        // Create an assistance
        Assistance assistance = new Assistance();
        assistance.setTitle("TestAssistanceTitle");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.PENDING);
        assistance.setType(Assistance.Type.OFFER);
        assistance.setCancelled(false);

        // Add it to the database with SQL
        String query = "INSERT INTO assistance (title, creator, description, createdAt, dueDate, status, type, isCancelled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        ps.executeUpdate();

        // Check that it is added
        String query2 = "SELECT * FROM assistance WHERE title=?";
        java.sql.PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, assistance.getTitle());
        java.sql.ResultSet rs2 = ps2.executeQuery();
        assertTrue(rs2.next());

        // test getAssistance
        AssistanceDaoImplementation adi = new AssistanceDaoImplementation();
        Assistance assistance2 = adi.getAssistance(rs2.getInt("id"));
        assertNotNull(assistance2);
        assertEquals(assistance.getTitle(), assistance2.getTitle());
        assertEquals(assistance.getCreatorId(), assistance2.getCreatorId());
        assertEquals(assistance.getDescription(), assistance2.getDescription());
        assertEquals(assistance.getCreatedAt().toString(), assistance2.getCreatedAt().toString());
        assertEquals(assistance.getDueDate().toString(), assistance2.getDueDate().toString());
        assertEquals(assistance.getStatus().toString(), assistance2.getStatus().toString());
        assertEquals(assistance.getType().toString(), assistance2.getType().toString());
        assertEquals(assistance.isCancelled(), assistance2.isCancelled());

        // Delete with SQL and id
        String query3 = "DELETE FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps3 = con.prepareStatement(query3);
        ps3.setInt(1, rs2.getInt("id"));
        ps3.executeUpdate();
    }

    @Test
    void getAssistances() throws SQLException {
        // Create some assistances
        Assistance assistance = new Assistance();
        assistance.setTitle("TestAssistanceTitle");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.PENDING);
        assistance.setType(Assistance.Type.OFFER);
        assistance.setCancelled(false);

        Assistance assistance2 = new Assistance();
        assistance2.setTitle("TestAssistanceTitle2");
        assistance2.setCreatorId(user.getId());
        assistance2.setDescription("TestAssistanceDescription2");
        assistance2.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance2.setDueDate(new Date(System.currentTimeMillis()));
        assistance2.setStatus(Assistance.Status.PENDING);
        assistance2.setType(Assistance.Type.OFFER);
        assistance2.setCancelled(false);


        // Add it to the database with SQL
        String query = "INSERT INTO assistance (title, creator, description, createdAt, dueDate, status, type, isCancelled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        ps.executeUpdate();

        ps.setString(1, assistance2.getTitle());
        ps.setInt(2, assistance2.getCreatorId());
        ps.setString(3, assistance2.getDescription());
        ps.setDate(4, assistance2.getCreatedAt());
        ps.setDate(5, assistance2.getDueDate());
        ps.setString(6, assistance2.getStatus().toString());
        ps.setString(7, assistance2.getType().toString());
        ps.setBoolean(8, assistance2.isCancelled());
        ps.executeUpdate();


        // Check that it is added
        String query2 = "SELECT * FROM assistance WHERE title=?";
        java.sql.PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, assistance.getTitle());
        java.sql.ResultSet rs2 = ps2.executeQuery();
        assertTrue(rs2.next());
        int assistanceId = rs2.getInt("id");

        ps2.setString(1, assistance2.getTitle());
        java.sql.ResultSet rs3 = ps2.executeQuery();
        assertTrue(rs3.next());
        int assistanceId2 = rs3.getInt("id");


        // test getAssistances
        AssistanceDaoImplementation adi = new AssistanceDaoImplementation();
        List<Assistance> assistances = adi.getAssistances();
        assertNotNull(assistances);
        assertEquals(2, assistances.size());

        // For each assistance check that it is the right attributes
        for (Assistance assistance3 : assistances) {
            if (assistance3.getTitle().equals(assistance.getTitle())) {
                assertEquals(assistance.getTitle(), assistance3.getTitle());
                assertEquals(assistance.getCreatorId(), assistance3.getCreatorId());
                assertEquals(assistance.getDescription(), assistance3.getDescription());
                assertEquals(assistance.getCreatedAt().toString(), assistance3.getCreatedAt().toString());
                assertEquals(assistance.getDueDate().toString(), assistance3.getDueDate().toString());
                assertEquals(assistance.getStatus().toString(), assistance3.getStatus().toString());
                assertEquals(assistance.getType().toString(), assistance3.getType().toString());
                assertEquals(assistance.isCancelled(), assistance3.isCancelled());
            } else if (assistance3.getTitle().equals(assistance2.getTitle())) {
                assertEquals(assistance2.getTitle(), assistance3.getTitle());
                assertEquals(assistance2.getCreatorId(), assistance3.getCreatorId());
                assertEquals(assistance2.getDescription(), assistance3.getDescription());
                assertEquals(assistance2.getCreatedAt().toString(), assistance3.getCreatedAt().toString());
                assertEquals(assistance2.getDueDate().toString(), assistance3.getDueDate().toString());
                assertEquals(assistance2.getStatus().toString(), assistance3.getStatus().toString());
                assertEquals(assistance2.getType().toString(), assistance3.getType().toString());
                assertEquals(assistance2.isCancelled(), assistance3.isCancelled());
            } else {
                fail("Unexpected assistance");
            }
        }

        // Delete with SQL and id
        String query3 = "DELETE FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps3 = con.prepareStatement(query3);
        ps3.setInt(1, assistanceId);
        ps3.executeUpdate();

        ps3.setInt(1, assistanceId2);
        ps3.executeUpdate();


    }


    @Test
    void update() throws SQLException {
        // Create an assistance
        Assistance assistance = new Assistance();
        assistance.setTitle("TestAssistanceTitle");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.PENDING);
        assistance.setType(Assistance.Type.OFFER);
        assistance.setCancelled(false);

        // Add it to the database with SQL
        String query = "INSERT INTO assistance (title, creator, description, createdAt, dueDate, status, type, isCancelled) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, assistance.getTitle());
        ps.setInt(2, assistance.getCreatorId());
        ps.setString(3, assistance.getDescription());
        ps.setDate(4, assistance.getCreatedAt());
        ps.setDate(5, assistance.getDueDate());
        ps.setString(6, assistance.getStatus().toString());
        ps.setString(7, assistance.getType().toString());
        ps.setBoolean(8, assistance.isCancelled());
        ps.executeUpdate();

        // Check that it is added
        String query2 = "SELECT * FROM assistance WHERE title=?";
        java.sql.PreparedStatement ps2 = con.prepareStatement(query2);
        ps2.setString(1, assistance.getTitle());
        java.sql.ResultSet rs2 = ps2.executeQuery();
        assertTrue(rs2.next());
        int assistanceId = rs2.getInt("id");

        // Update the assistance
        assistance.setId(assistanceId);
        assistance.setTitle("TestAssistanceTitle2");
        assistance.setCreatorId(user.getId());
        assistance.setDescription("TestAssistanceDescription2");
        assistance.setCreatedAt(new Date(System.currentTimeMillis()));
        assistance.setDueDate(new Date(System.currentTimeMillis()));
        assistance.setStatus(Assistance.Status.ACCEPTED);
        assistance.setType(Assistance.Type.REQUEST);
        assistance.setCancelled(true);

        // Update with DAO
        AssistanceDaoImplementation adi = new AssistanceDaoImplementation();
        adi.update(assistance);

        // Check that it is updated
        String query3 = "SELECT * FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps3 = con.prepareStatement(query3);
        ps3.setInt(1, assistanceId);
        java.sql.ResultSet rs3 = ps3.executeQuery();
        assertTrue(rs3.next());
        assertEquals(assistance.getTitle(), rs3.getString("title"));
        assertEquals(assistance.getCreatorId(), rs3.getInt("creator"));
        assertEquals(assistance.getDescription(), rs3.getString("description"));
        assertEquals(assistance.getCreatedAt().toString(), rs3.getDate("createdAt").toString());
        assertEquals(assistance.getDueDate().toString(), rs3.getDate("dueDate").toString());
        assertEquals(assistance.getStatus().toString(), rs3.getString("status"));
        assertEquals(assistance.getType().toString(), rs3.getString("type"));
        assertEquals(assistance.isCancelled(), rs3.getBoolean("isCancelled"));

        // Delete with SQL and id
        String query4 = "DELETE FROM assistance WHERE id=?";
        java.sql.PreparedStatement ps4 = con.prepareStatement(query4);
        ps4.setInt(1, assistanceId);
        ps4.executeUpdate();
    }


    @AfterEach
    void tearDown() {
        // Delete the user
        try {
            udi.delete(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}