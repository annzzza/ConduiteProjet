package com.example.conduiteprojet;

import com.example.conduiteprojet.Assistance;

import java.sql.SQLException;
import java.util.List;

public interface AssistanceDAO {

    public void add(Assistance assistance)
        throws SQLException;
    public void delete(int id)
        throws  SQLException;
    public Assistance getAssistance(int id)
        throws SQLException;
    public List<Assistance> getAssistances()
        throws SQLException;


    public List<Assistance> getAssistanceOffers()
            throws SQLException;

    public List<Assistance> getAssistanceRequests()
            throws SQLException;


    public void update(Assistance assistance)
        throws SQLException;
}
