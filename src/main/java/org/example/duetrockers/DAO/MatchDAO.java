package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.Match;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Skapa

    public boolean saveMatch(Match match) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(match);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            if (em != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }

        return result;
    }

    //Read

    public Match getMatchByID(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Match match = em.find(Match.class, id);
        em.close();
        return match;
    }

    //Read alla matcher
    public List<Match> getAllMatches() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Match> matches = new ArrayList<>();
        TypedQuery<Match> query = em.createQuery("FROM Match", Match.class);
        matches.addAll(query.getResultList());
        em.close();
        return matches;
    }

    //Update
    public boolean updateMatch(Match matchToUpdate) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(em.contains(matchToUpdate) ? matchToUpdate : em.merge(matchToUpdate));
            transaction.commit();
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            if (em != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }

        return result;
    }

    //Delete

    public boolean deleteMatch(Match matchToDelete) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            if (!em.contains(matchToDelete)) {
                matchToDelete = em.merge(matchToDelete);
            }
            em.remove(matchToDelete);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            if (em != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }

        return result;
    }
}

