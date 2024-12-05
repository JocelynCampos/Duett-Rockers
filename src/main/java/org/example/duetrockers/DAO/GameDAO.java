package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.Game;

import java.util.ArrayList;
import java.util.List;

public class GameDAO
{

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    //CRUD operations

    //CREATE
    public boolean saveGame(Game game)
    {

        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(game);
            transaction.commit();
            result = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());

            if(em != null && transaction != null && transaction.isActive())
            {
                transaction.rollback();
            }
        }
        finally
        {
            em.close();
        }

        return result;
    }

    //READ One or ALL
    public Game getGameByID(int id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Game gameToReturn = em.find(Game.class, id);
        em.close();
        return gameToReturn;
    }

    public List<Game> getAllGames()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> listToReturn = new ArrayList<>();
        TypedQuery<Game> query = em.createQuery("FROM Game", Game.class);
        listToReturn.addAll(query.getResultList());
        return listToReturn;
    }

    //UPDATE
    public boolean updateGame(Game gameToUpdate)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(em.contains(gameToUpdate) ? gameToUpdate : em.merge(gameToUpdate));
            transaction.commit();
            result = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            if(em != null && transaction != null && transaction.isActive())
            {
                transaction.rollback();
            }
        }
        finally
        {
            em.close();
        }

        return result;
    }

    //DELETE
    public boolean deleteGame(Game gameToDelete)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            if(!em.contains(gameToDelete))
            {
                gameToDelete = em.merge(gameToDelete);
            }
            em.remove(gameToDelete);
            transaction.commit();
            result = true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            if(em != null && transaction != null && transaction.isActive())
            {
                transaction.rollback();
            }
        }
        finally
        {
            em.close();
        }

        return result;
    }
}
