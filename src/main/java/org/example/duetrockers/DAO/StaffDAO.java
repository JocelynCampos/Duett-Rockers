package org.example.duetrockers.DAO;

import jakarta.persistence.*;
import org.example.duetrockers.entities.Person;
import org.example.duetrockers.entities.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //Create
    public boolean saveStaff(Staff staff, Person person)
    {
        boolean result = false;

        PersonDAO personDAO = new PersonDAO();

        if(personDAO.savePerson(person))
        {
            EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;

            try
            {
                transaction = em.getTransaction();
                transaction.begin();
                em.merge(staff);
                transaction.commit();
                result = true;
            }
            catch(Exception e)
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

        }
        return result;
    }

    //Read One or All
    public Staff getStaffbyID(int id)
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Staff staffToReturn = em.find(Staff.class, id);
        return staffToReturn;
    }

    public List<Staff> getAllStaff()
    {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Staff> listToReturn = new ArrayList<>();
        TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s JOIN FETCH s.person", Staff.class);
        listToReturn.addAll(query.getResultList());
        return listToReturn;
    }

    //Update
    public boolean updateStaff(Staff staffToUpdate)
    {
        boolean result = false;

        PersonDAO personDAO = new PersonDAO();

        if(personDAO.updatePerson(staffToUpdate.getPerson()))
        {
            EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try
            {
                transaction = em.getTransaction();
                transaction.begin();
                em.persist(em.contains(staffToUpdate) ? staffToUpdate : em.merge(staffToUpdate));
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
        }

        return result;
    }

    //Delete
    public boolean deleteStaff(Staff staffToDelete)
    {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try
        {
            transaction = em.getTransaction();
            transaction.begin();
            if(!em.contains(staffToDelete))
            {
                staffToDelete = em.merge(staffToDelete);
            }
            em.remove(staffToDelete);
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
