package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    //Create
    public boolean savePerson(Person person)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(person);
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

    //Read One or All
    public Person getPersonbyID(int id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Person personToReturn = em.find(Person.class, id);
        return personToReturn;
    }

    public List<Person> getAllPersons()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Person> listToReturn = new ArrayList<>();
        TypedQuery<Person> query = em.createQuery("FROM Person s", Person.class);
        listToReturn.addAll(query.getResultList());
        return listToReturn;
    }

    //Update
    public boolean updatePerson(Person personToUpdate)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(em.contains(personToUpdate) ? personToUpdate : em.merge(personToUpdate));
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

    //Delete
    public boolean deletePerson(Person personToDelete)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            if(!em.contains(personToDelete))
            {
                personToDelete = em.merge(personToDelete);
            }
            em.remove(personToDelete);
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
