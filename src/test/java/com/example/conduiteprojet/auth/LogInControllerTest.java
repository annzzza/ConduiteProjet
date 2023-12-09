package com.example.conduiteprojet.auth;

import com.example.conduiteprojet.database.UserDaoImplementation;
import com.example.conduiteprojet.utils.PreferencesManager;
import com.example.conduiteprojet.utils.Security;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LogInControllerTest {

    static User registeredUser;
    static String registeredUserClearPassword = "TestLogInPassword";
    @BeforeEach
    void setUp() throws SQLException {
        User newUser = new User();
        String username = "TestLogInUsername";
        newUser.setUsername(username);
        newUser.setFirstName("TestLogInFirstName");
        newUser.setLastName("TestLogInLastName");
        newUser.setPassword(Security.getMd5(registeredUserClearPassword));
        newUser.setRole(User.Role.PATIENT);
        UserDaoImplementation udi = new UserDaoImplementation();
        udi.add(newUser);
        registeredUser = udi.getUser(username);
    }
    @Test
    void testThatUserIsConnectedWithValidCredentials() throws SQLException {

        String usernameTest = registeredUser.getUsername();
        String passwordTest = "TestLogInPassword";


        assertTrue(LogInController.login(usernameTest, passwordTest));

        // Check that it is saved in preferences
        UserDaoImplementation udi = new UserDaoImplementation();
        User registeredUser = udi.getUser(usernameTest);

        assertEquals(PreferencesManager.getID(), registeredUser.getId());
        assertEquals(PreferencesManager.getRole(), registeredUser.getRole());
    }

    @Test
    void testThatUserIsConnectedWithWrongCredentials() {

        String usernameTest = "TestLogInUsername";
        String passwordTest = "WrongPassword";


        assertFalse(LogInController.login(usernameTest, passwordTest));
    }


    @AfterEach
    void tearDown() throws SQLException {
        UserDaoImplementation udi = new UserDaoImplementation();
        udi.delete(registeredUser.getId());
    }
}