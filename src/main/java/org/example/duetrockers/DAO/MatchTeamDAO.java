package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.MatchTeam;
import org.example.duetrockers.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class MatchTeamDAO {
    //CRUD - operations

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
    public boolean saveMatchTeam(MatchTeam matchTeam) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(matchTeam);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    //Read ONE
    public MatchTeam getMatchTeamById(int id) {

        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return entityManager.find(MatchTeam.class, id);
        }
    }

    //Read ALL
    public List<MatchTeam> getAllMatchTeams() {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return entityManager.createQuery("FROM MatchTeam", MatchTeam.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error when getting all teams.." + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Update
    public boolean updateMatchTeam(MatchTeam matchTeam) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            System.out.println("Test, starting transaction...");
            transaction.begin();
            System.out.println("Test successful.");
            entityManager.merge(matchTeam);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    //Delete
    public boolean deleteMatchTeam(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            MatchTeam matchTeam = entityManager.find(MatchTeam.class, id);

            if (matchTeam != null) { //Om objekt hittat
                entityManager.remove(matchTeam);
                transaction.commit(); //Tar då bort
                return true;
            } else { //om objektet inte hittades
                System.out.println("Match with ID " + id + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return false;

    }

    public boolean ifMatchExists(Team team1, Team team2) {
        List<MatchTeam> existingMatches = getAllMatchTeams();

        for (MatchTeam match : existingMatches) {
            boolean sameTeam = (match.getTeam1().equals(team1) && match.getTeam2().equals(team2)) ||
            (match.getTeam1().equals(team2) && match.getTeam2().equals(team1));

            if (sameTeam) {
                return true;
            }
        }
        return false;
    }
}
