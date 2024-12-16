package org.example.duetrockers.DAO;
import jakarta.persistence.*;
import org.example.duetrockers.entities.Team;
import java.util.List;
import java.util.ArrayList;


public class TeamDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
//Skapa
    public boolean saveTeam(Team team) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(team);
            transaction.commit();
            result = true;
        }catch (Exception e) {
            System.out.println("Fel vid lagring av team: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }finally {
            em.close();
        }
        return result;
        }

        //Read
        public Team getTeamBYID(int id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team teamToReturn = em.find(Team.class, id);
        em.close();
        return teamToReturn;
        }

    public List<Team> getAllTeams() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> query = em.createQuery("FROM Team", Team.class);
        listToReturn.addAll(query.getResultList());
        em.close();
        return listToReturn;
    }

    //update
    public boolean updateTeam(Team teamToUpdate) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(teamToUpdate);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            System.out.println("Fel vid uppdatering av team: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }

        return result;
    }


    //Delete
    public boolean deleteTeam(Team teamToDelete) {
        boolean result = false;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            if (!em.contains(teamToDelete)) {
                teamToDelete = em.merge(teamToDelete);
            }
            em.remove(teamToDelete);
            transaction.commit();
            result = true;

        } catch (Exception e) {
            System.out.println("Fel vid borttagning av team: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            em.close();
        }

        return result;
    }

    public Team getTeamByName (String teamName) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team team = null;

        try {
            team = em.createQuery("Select t FROM Team t WHERE t.teamName = :name", Team.class)
                    .setParameter("name", teamName)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No team found with name: " + teamName);
        } finally {
            em.close();
        }
        return team;
    }
}

