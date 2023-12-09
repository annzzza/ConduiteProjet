package com.example.conduiteprojet.utils;

import com.example.conduiteprojet.auth.User;

import java.util.prefs.Preferences;

public class PreferencesManager {

    /**
     * A local database to save preferences.
     */
    private static final Preferences preferences = Preferences.userNodeForPackage(PreferencesManager.class);

    /**
     * Save the role of connected user.
     * @param role Role of connected user
     */
    public static void saveRole(User.Role role) {
        preferences.put("userRole", role.toString());
    }

    /**
     * @return the role of connected user.
     */
    public static User.Role getRole() {
        return User.Role.valueOf(preferences.get("userRole", User.Role.PATIENT.toString()));
    }

    /**
     * Save the ID of connected user.
     * @param id ID of connected user
     */
    public static void saveID(int id) {
        preferences.put("userId", String.valueOf(id));
    }

    /**
     * Get the ID of connected user.
     * @return ID of connected user.
     */
    public static int getID() { return Integer.parseInt(preferences.get("userId", "-1")); }
}
