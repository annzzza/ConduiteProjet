package com.example.conduiteprojet.auth;

import com.example.conduiteprojet.database.UserDaoImplementation;
import com.example.conduiteprojet.utils.Security;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {
    static User userTest1 = new User();
    @AfterAll
    static void afterAll() {
        UserDaoImplementation udi = new UserDaoImplementation();
        try {
            User user = udi.getUser(userTest1.getUsername());
            if( user != null) {
                udi.delete(user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void register() throws SQLException {
        // Valid user
        String username = "TestRegisterUsername";
        userTest1.setUsername(username);
        userTest1.setFirstName("TestRegisterFirstName");
        userTest1.setLastName("TestRegisterLastName");
        userTest1.setPassword(Security.getMd5("TestRegisterPassword"));
        userTest1.setRole(User.Role.PATIENT);

        // Registration
        boolean registered = RegisterController.register(userTest1);

        // Check that it is registered
        assertTrue(registered);

        // Check that it is saved in preferences
        UserDaoImplementation udi = new UserDaoImplementation();
        User registeredUser = udi.getUser(username);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
    }

    @Test
    void registerWhenAlreadyExist() {
        // Valid user
        String username = "TestRegisterUsername";
        userTest1.setUsername(username);
        userTest1.setFirstName("TestRegisterFirstName");
        userTest1.setLastName("TestRegisterLastName");
        userTest1.setPassword(Security.getMd5("TestRegisterPassword"));
        userTest1.setRole(User.Role.PATIENT);

        User userTest2 = new User();
        userTest2.setUsername(username);
        userTest2.setFirstName("TestRegisterFirstName2");
        userTest2.setLastName("TestRegisterLastName2");
        userTest2.setPassword(Security.getMd5("TestRegisterPassword2"));
        userTest2.setRole(User.Role.BENEVOLE);

        // Registration with same username
        RegisterController.register(userTest1);
        boolean registered = RegisterController.register(userTest2);

        // Check that you cannot register with the same username
        assertFalse(registered);
    }

}