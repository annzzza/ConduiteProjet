package com.example.conduiteprojet;

import java.util.prefs.Preferences;

public class PreferencesManager {

    private static final Preferences preferences = Preferences.userNodeForPackage(PreferencesManager.class);

    public static void saveRole(String role) {
        preferences.put("userRole", role);
    }

    public static String getRole() {
        return preferences.get("userRole", "Benevole");
    }

    public static void saveUserID(int userID) { preferences.put("userID", String.valueOf(userID)); }

    public static String getUserIDString() { return preferences.get("userName", "-1"); }
}
