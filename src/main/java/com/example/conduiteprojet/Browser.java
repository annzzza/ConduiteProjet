package com.example.conduiteprojet;

import java.util.ArrayList;
import java.util.List;

public class Browser {

    public List<Assistance> getCorrespondingList() {
        String role = PreferencesManager.getRole();

        if (role.equals("Benevole")) {

        } else {

        }
        return new ArrayList<Assistance>();
    }
}
