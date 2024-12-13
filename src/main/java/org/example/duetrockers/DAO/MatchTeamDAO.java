package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.MatchTeam;

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
            transaction.begin();
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
                transaction.rollback(); //återgå
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
}
