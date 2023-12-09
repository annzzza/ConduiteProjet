package com.example.conduiteprojet.utils;

import java.util.prefs.Preferences;

public class PreferencesManager {

    private static final Preferences preferences = Preferences.userNodeForPackage(PreferencesManager.class);

    public static void saveRole(String role) {
        preferences.put("userRole", role);
    }

    public static String getRole() {
        return preferences.get("userRole", "Benevole");
    }
}
