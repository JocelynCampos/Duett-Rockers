package org.example.duetrockers.DAO;

import jakarta.persistence.criteria.From;
import org.example.duetrockers.entities.Persons;
import org.example.duetrockers.entities.Persons;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonDAO {
    private Session session;

    public PersonDAO (Session session) {
        this.session = session;
    }

    public Persons getPersonById (int id) {
        return session.get(Persons.class, id);
    }

    public void showPersons () {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Persons> persons = session.createQuery("FROM Person", Persons.class).list();
            for (Persons person : persons) {
                System.out.println(person); //Skriv ut varje person
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void savePerson (Persons person) {
        session.persist(person);
    }

    public void addPerson (Persons person) {
        savePerson(person);

    }

    public void removePerson (int id) {
        Transaction transaction = session.beginTransaction();
        Persons person = session.get(Persons.class, id);
        if (person != null) {
            session.remove(person);
        }
    }
}
