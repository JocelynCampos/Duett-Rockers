package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.MatchPlayer;

import java.util.ArrayList;
import java.util.List;

public class MatchPlayerDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Skapa ny MatchPlayer

    public boolean saveMatchPlayer(MatchPlayer matchPlayer) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(matchPlayer);
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

    //Read en MAtchPlayer baserat på ID

    public MatchPlayer getMatchPlayerByID(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        MatchPlayer matchPlayer = em.find(MatchPlayer.class, id);
        em.close();
        return matchPlayer;
    }

    //Read alla MatchPlayer

    public List<MatchPlayer> getAllMatchPlayers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<MatchPlayer> matchPlayers = new ArrayList<>();
        TypedQuery<MatchPlayer> query = em.createQuery("FROM MatchPlayer", MatchPlayer.class);
        matchPlayers.addAll(query.getResultList());
        em.close();
        return matchPlayers;
    }

    //Update

    public boolean updateMatchPlayer(MatchPlayer matchPlayerToUpdate) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(em.contains(matchPlayerToUpdate) ? matchPlayerToUpdate : em.merge(matchPlayerToUpdate));
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

    //Ta bort en MatchPlayer

    public boolean deleteMatchPlayer(MatchPlayer matchPlayerToDelete) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            if (!em.contains(matchPlayerToDelete)) {
                matchPlayerToDelete = em.merge(matchPlayerToDelete);
            }
            em.remove(matchPlayerToDelete);
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