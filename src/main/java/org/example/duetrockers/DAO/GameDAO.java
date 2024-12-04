package org.example.duetrockers.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.duetrockers.entities.Game;

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

    //UPDATE

    //DELETE
}
