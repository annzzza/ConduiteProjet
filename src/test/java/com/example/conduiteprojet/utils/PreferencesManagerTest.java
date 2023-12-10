package com.example.conduiteprojet.utils;

import com.example.conduiteprojet.auth.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesManagerTest {

    @Test
    void ID() {
        int idTest = 41; // Should be different from default value

        PreferencesManager.saveID(41);

        assertEquals(idTest, PreferencesManager.getID());
    }

    @Test
    void Role() {
        User.Role roleTest = User.Role.VALIDEUR; // Should be different from default value

        PreferencesManager.saveRole(roleTest);

        assertEquals(roleTest, PreferencesManager.getRole());
    }
}