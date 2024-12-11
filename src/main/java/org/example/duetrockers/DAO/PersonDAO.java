package org.example.duetrockers.DAO;

import org.example.duetrockers.entities.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonDAO {
    private Session session;

    public PersonDAO (Session session) {
        this.session = session;
    }

    public Person getPersonById (int id) {
        return session.get(Person.class, id);
    }

    public void showPersons () {
        Transaction transaction = session.beginTransaction();
        //session.
    }

    public void savePerson (Person person) {
        session.persist(person);
    }

    public void addPerson (Person person) {
        savePerson(person);

    }

    public void deletePerson (int id) {
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, id);
        if (person != null) {
            session.remove(person);
        }
    }
}
