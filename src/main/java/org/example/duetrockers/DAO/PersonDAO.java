package org.example.duetrockers.DAO;

import jakarta.persistence.criteria.From;
import org.example.duetrockers.entities.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonDAO {
    private Session session;

    public PersonDAO (Session session) {
        this.session = session;
    }

    public Person getPersonById (int id) {
        return session.get(Person.class, id);
    }

    public void showPersons () {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Person> persons = session.createQuery("FROM Person", Person.class).list();
            for (Person person : persons) {
                System.out.println(person); //Skriv ut varje person
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void savePerson (Person person) {
        session.persist(person);
    }

    public void addPerson (Person person) {
        savePerson(person);

    }

    public void removePerson (int id) {
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, id);
        if (person != null) {
            session.remove(person);
        }
    }
}
