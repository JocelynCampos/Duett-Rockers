package org.example.duetrockers.DAO;

public class PlayerDAO
{
import jakarta.persistence.*;
import org.example.duetrockers.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Skapa
    public boolean savePlayers (Player player){
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin ();
            em.persist(player);
            transaction.commit();
            result = true;
        }catch (Exception e) {
            System.out.println(e.getMessage());

            if (em != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }finally {
            em.close();
        }

        return result;
    }
//READ
    public Player getPlayerByID(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Player playerToReturn = em.find(Player.class, id);
        em.close();
        return playerToReturn;

    }

    public List<Player> getAllPlayers(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Player> listToReturn = new ArrayList<>();
        TypedQuery<Player> query = em.createQuery("From Player", Player.class);
        em.close();
        return listToReturn;
    }



// update
    public boolean updatePlayer(Player playerToUpdate) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(em.contains(playerToUpdate) ? playerToUpdate : em.merge(playerToUpdate));
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
    public boolean deletePlayer(Player playerToDelete) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            if (!em.contains(playerToDelete)) {
                playerToDelete = em.merge(playerToDelete);
            }
            em.remove(playerToDelete);
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

