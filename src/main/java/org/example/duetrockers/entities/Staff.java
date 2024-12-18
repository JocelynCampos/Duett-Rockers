package org.example.duetrockers.entities;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "Staff")
public class Staff
{
    @Id
    @Column(name = "person_id")
    private int id;

    @OneToOne(optional = false, fetch = EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;

    public Staff()
    {

    }

    public Staff(Person person)
    {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString()
    {
        return person.getFirstName() + " " + person.getLastName();
    }
}
